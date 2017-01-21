package org.unclesniper.json.tool;

import org.unclesniper.json.tool.syntax.Selector;
import org.unclesniper.json.tool.syntax.Transform;
import org.unclesniper.json.tool.syntax.NameValue;
import org.unclesniper.json.tool.syntax.ThisValue;
import org.unclesniper.json.tool.syntax.IntLiteral;
import org.unclesniper.json.tool.syntax.SimpleValue;
import org.unclesniper.json.tool.syntax.Subselector;
import org.unclesniper.json.tool.syntax.Construction;
import org.unclesniper.json.tool.syntax.ComplexValue;
import org.unclesniper.json.tool.syntax.NonNameValue;
import org.unclesniper.json.tool.syntax.FloatLiteral;
import org.unclesniper.json.tool.syntax.WithComponent;
import org.unclesniper.json.tool.syntax.SelectedValue;
import org.unclesniper.json.tool.syntax.StringLiteral;
import org.unclesniper.json.tool.syntax.TransformChain;
import org.unclesniper.json.tool.syntax.IndexDirectKey;
import org.unclesniper.json.tool.syntax.RangeDirectKey;
import org.unclesniper.json.tool.syntax.CallExpression;
import org.unclesniper.json.tool.syntax.RemoveComponents;
import org.unclesniper.json.tool.syntax.RetainComponents;
import org.unclesniper.json.tool.syntax.ValueIndirectKey;
import org.unclesniper.json.tool.syntax.RangeIndirectKey;
import org.unclesniper.json.tool.syntax.PrefixExpression;
import org.unclesniper.json.tool.syntax.ReplaceComponents;
import org.unclesniper.json.tool.syntax.ValueConstruction;
import org.unclesniper.json.tool.syntax.ArrayConstruction;
import org.unclesniper.json.tool.syntax.DirectSubselector;
import org.unclesniper.json.tool.syntax.PropertyDirectKey;
import org.unclesniper.json.tool.syntax.ConstructionValue;
import org.unclesniper.json.tool.syntax.ConstantDefinition;
import org.unclesniper.json.tool.syntax.ObjectConstruction;
import org.unclesniper.json.tool.syntax.ByValueSubselector;
import org.unclesniper.json.tool.syntax.EqualityExpression;
import org.unclesniper.json.tool.syntax.UntransformedValue;
import org.unclesniper.json.tool.syntax.IndirectSubselector;
import org.unclesniper.json.tool.syntax.SelectorIndirectKey;
import org.unclesniper.json.tool.syntax.InequalityExpression;
import org.unclesniper.json.tool.syntax.ByLetValueSubselector;
import org.unclesniper.json.tool.syntax.ByEqualValueSubselector;
import org.unclesniper.json.tool.syntax.BinaryLogicalExpression;
import org.unclesniper.json.tool.syntax.BinaryBitwiseExpression;
import org.unclesniper.json.tool.syntax.TransformationExpression;
import org.unclesniper.json.tool.syntax.ByUnequalValueSubselector;
import org.unclesniper.json.tool.syntax.BinaryArithmeticExpression;

public class Parser {

	private final Lexer lexer;

	private Token token;

	public Parser(Lexer lexer) {
		this.lexer = lexer;
	}

	public Lexer getLexer() {
		return lexer;
	}

	private void next() throws LexicalException {
		token = lexer.lex();
	}

	private void more(String expected) throws UnexpectedEndOfTransformException {
		if(token == null)
			throw new UnexpectedEndOfTransformException(expected, lexer.getInput().length());
	}

	private void unexpected(String expected) throws UnexpectedTokenException {
		throw new UnexpectedTokenException(token, expected);
	}

	public Transform parse() throws CompilationException {
		next();
		Transform transform = parseTransform();
		if(token != null)
			unexpected("end of transform");
		return transform;
	}

	private Transform parseTransform() throws CompilationException {
		more("transform");
		switch(token.getType()) {
			case DOT:
			case SLASH:
			case AT:
			case LEFT_CURLY:
			case LEFT_SQUARE:
			case LEFT_ANGLE:
			case NAME:
				return parsePlainTransform();
			case LEFT_ROUND:
				{
					TransformChain chain = new TransformChain(token.getOffset());
					next();
					chain.addTransform(parseTransform());
					for(;;) {
						more("',' or ')'");
						switch(token.getType()) {
							case COMMA:
								next();
								chain.addTransform(parseTransform());
								break;
							case RIGHT_ROUND:
								next();
								return chain;
							default:
								unexpected("',' or ')'");
						}
					}
				}
			default:
				unexpected("transform");
				return null;
		}
	}

	private Transform parsePlainTransform() throws CompilationException {
		more("plain transform");
		switch(token.getType()) {
			case DOT:
			case SLASH:
			case AT:
				return parseWithComponent();
			case LEFT_CURLY:
			case LEFT_SQUARE:
			case LEFT_ANGLE:
				return parseConstruction();
			case NAME:
				return parseConstantDefinition();
			default:
				unexpected("plain transform");
				return null;
		}
	}

	private WithComponent parseWithComponent() throws CompilationException {
		Selector selector = parseSelector();
		if(token == null)
			return new RetainComponents(-1, selector);
		int offset;
		switch(token.getType()) {
			case PLUS:
				offset = token.getOffset();
				next();
				return new RetainComponents(offset, selector);
			case MINUS:
				offset = token.getOffset();
				next();
				return new RemoveComponents(offset, selector);
			case ASSIGN:
				offset = token.getOffset();
				next();
				return new ReplaceComponents(offset, selector, parseComplexValue());
			default:
				return new RetainComponents(-1, selector);
		}
	}

	private Construction parseConstruction() throws CompilationException {
		switch(token.getType()) {
			case LEFT_CURLY:
				return parseObjectConstruction();
			case LEFT_SQUARE:
				return parseArrayConstruction();
			case LEFT_ANGLE:
				return parseValueConstruction();
			default:
				unexpected("construction");
				return null;
		}
	}

	private ObjectConstruction parseObjectConstruction() throws CompilationException {
		ObjectConstruction object = new ObjectConstruction(token.getOffset());
		next();
		more("binding or '}'");
		switch(token.getType()) {
			case LEFT_SQUARE:
			case LEFT_CURLY:
			case STRING:
			case INT:
			case FLOAT:
			case NAME:
			case THIS:
			case LEFT_ANGLE:
				object.addBinding(parseBinding());
				for(;;) {
					more("',' or '}'");
					switch(token.getType()) {
						case COMMA:
							next();
							object.addBinding(parseBinding());
							break;
						case RIGHT_CURLY:
							next();
							return object;
						default:
							unexpected("',' or '}'");
					}
				}
			case RIGHT_CURLY:
				next();
				return object;
			default:
				unexpected("binding or '}'");
				return null;
		}
	}

	private ObjectConstruction.Binding parseBinding() throws CompilationException {
		more("binding");
		SimpleValue key;
		boolean direct;
		switch(token.getType()) {
			case NAME:
				key = new NameValue(token.getOffset(), token.getText());
				next();
				more("':' or '='");
				switch(token.getType()) {
					case COLON:
						direct = true;
						break;
					case ASSIGN:
						direct = false;
						break;
					default:
						unexpected("':' or '='");
						return null;
				}
				break;
			case LEFT_SQUARE:
			case LEFT_CURLY:
			case STRING:
			case INT:
			case FLOAT:
			case THIS:
			case LEFT_ANGLE:
				key = parseNonNameValue();
				more("'='");
				if(token.getType() != Token.Type.ASSIGN)
					unexpected("'='");
				direct = false;
				break;
			default:
				unexpected("binding");
				return null;
		}
		int offset = token.getOffset();
		next();
		ComplexValue value = parseComplexValue();
		return new ObjectConstruction.Binding(offset, key, value, direct);
	}

	private ArrayConstruction parseArrayConstruction() throws CompilationException {
		ArrayConstruction array = new ArrayConstruction(token.getOffset());
		next();
		more("complex value or ']'");
		switch(token.getType()) {
			case TILDE:
			case NOT:
			case MINUS:
			case PLUS:
			case LEFT_CURLY:
			case LEFT_SQUARE:
			case LEFT_ANGLE:
			case STRING:
			case INT:
			case FLOAT:
			case NAME:
			case THIS:
				array.addElement(parseComplexValue());
				for(;;) {
					more("',' or ']'");
					switch(token.getType()) {
						case COMMA:
							next();
							array.addElement(parseComplexValue());
							break;
						case RIGHT_SQUARE:
							next();
							return array;
						default:
							unexpected("',' or ']'");
					}
				}
			case RIGHT_SQUARE:
				next();
				return array;
			default:
				unexpected("complex value or ']'");
				return null;
		}
	}

	private ValueConstruction parseValueConstruction() throws CompilationException {
		int offset = token.getOffset();
		next();
		ComplexValue value = parseComplexValue();
		more("'>'");
		if(token.getType() != Token.Type.RIGHT_ANGLE)
			unexpected("'>'");
		next();
		return new ValueConstruction(offset, value);
	}

	private ConstantDefinition parseConstantDefinition() throws CompilationException {
		String name = token.getText();
		next();
		more("'='");
		if(token.getType() != Token.Type.ASSIGN)
			unexpected("'='");
		int offset = token.getOffset();
		next();
		ComplexValue value = parseComplexValue();
		return new ConstantDefinition(offset, name, value);
	}

	private Selector parseSelector() throws CompilationException {
		more("selector");
		Selector selector = new Selector(token.getOffset());
		selector.addSubselector(parseSubselector());
		while(token != null) {
			switch(token.getType()) {
				case DOT:
				case SLASH:
				case AT:
					selector.addSubselector(parseSubselector());
					break;
				default:
					return selector;
			}
		}
		return selector;
	}

	private Subselector parseSubselector() throws CompilationException {
		more("subselector");
		int offset = token.getOffset();
		switch(token.getType()) {
			case DOT:
				return parseDirectSubselector();
			case SLASH:
				return parseIndirectSubselector();
			case AT:
				return parseByValueSubselector();
			default:
				unexpected("subselector");
				return null;
		}
	}

	private int parseArrayIndex() throws UnexpectedTokenException {
		try {
			return Integer.parseInt(token.getText());
		}
		catch(NumberFormatException nfe) {
			unexpected("sufficiently small array index (int, not long)");
			return 0;
		}
	}

	private DirectSubselector parseDirectSubselector() throws CompilationException {
		DirectSubselector subselector = new DirectSubselector(token.getOffset());
		next();
		more("direct key");
		switch(token.getType()) {
			case NAME:
			case STRING:
				subselector.addKey(new PropertyDirectKey(token.getOffset(), token.getText()));
				next();
				break;
			case INT:
				{
					int offset = token.getOffset();
					int lowerBound = parseArrayIndex();
					next();
					if(token != null && token.getType() == Token.Type.COLON) {
						offset = token.getOffset();
						next();
						more("integer literal");
						if(token.getType() != Token.Type.INT)
							unexpected("integer literal");
						int upperBound = parseArrayIndex();
						next();
						subselector.addKey(new RangeDirectKey(offset, lowerBound, upperBound));
					}
					else
						subselector.addKey(new IndexDirectKey(offset, lowerBound));
				}
				break;
			case LEFT_ROUND:
				next();
				more("direct key");
				switch(token.getType()) {
					case NAME:
					case STRING:
						subselector.addKey(new PropertyDirectKey(token.getOffset(), token.getText()));
						next();
						for(;;) {
							more("',' or ')'");
							if(token.getType() == Token.Type.COMMA) {
								next();
								more("name or string literal");
								if(token.getType() != Token.Type.NAME && token.getType() != Token.Type.STRING)
									unexpected("name or string literal");
								subselector.addKey(new PropertyDirectKey(token.getOffset(), token.getText()));
								next();
							}
							else if(token.getType() == Token.Type.RIGHT_ROUND) {
								next();
								break;
							}
							else
								unexpected("',' or ')'");
						}
						break;
					case INT:
						{
							int offset = token.getOffset();
							int lowerBound = parseArrayIndex();
							next();
							more("':', ',' or ')'");
							if(token.getType() == Token.Type.COLON) {
								offset = token.getOffset();
								more("integer literal");
								if(token.getType() != Token.Type.INT)
									unexpected("integer literal");
								int upperBound = parseArrayIndex();
								next();
								subselector.addKey(new RangeDirectKey(offset, lowerBound, upperBound));
							}
							else
								subselector.addKey(new IndexDirectKey(offset, lowerBound));
							for(;;) {
								more("',' or ')'");
								if(token.getType() == Token.Type.COMMA) {
									next();
									more("integer literal");
									if(token.getType() != Token.Type.INT)
										unexpected("integer literal");
									offset = token.getOffset();
									lowerBound = parseArrayIndex();
									next();
									more("':', ',' or ')'");
									if(token.getType() == Token.Type.COLON) {
										offset = token.getOffset();
										more("integer literal");
										if(token.getType() != Token.Type.INT)
											unexpected("integer literal");
										int upperBound = parseArrayIndex();
										next();
										subselector.addKey(new RangeDirectKey(offset, lowerBound, upperBound));
									}
									else
										subselector.addKey(new IndexDirectKey(offset, lowerBound));
								}
								else if(token.getType() == Token.Type.RIGHT_ROUND) {
									next();
									break;
								}
								else
									unexpected("',' or ')'");
							}
						}
						break;
					default:
						unexpected("direct key");
						return null;
				}
			default:
				unexpected("direct key");
				return null;
		}
		return subselector;
	}

	private IndirectSubselector parseIndirectSubselector() throws CompilationException {
		IndirectSubselector subselector = new IndirectSubselector(token.getOffset());
		next();
		more("indirect key");
		SimpleValue lowerBound;
		int offset;
		switch(token.getType()) {
			case NAME:
			case STRING:
			case INT:
			case FLOAT:
			case THIS:
			case LEFT_CURLY:
			case LEFT_SQUARE:
			case LEFT_ANGLE:
				lowerBound = parseValue();
				offset = lowerBound.getOffset();
				if(token != null && token.getType() == Token.Type.COLON) {
					offset = token.getOffset();
					next();
					SimpleValue upperBound = parseValue();
					subselector.addKey(new RangeIndirectKey(offset, lowerBound, upperBound));
				}
				else
					subselector.addKey(new ValueIndirectKey(lowerBound));
				break;
			case LEFT_ROUND:
				next();
				more("indirect key");
				lowerBound = parseValue();
				offset = lowerBound.getOffset();
				more("':', selector, ',' or ')'");
				if(token.getType() == Token.Type.COLON) {
					offset = token.getOffset();
					next();
					SimpleValue upperBound = parseValue();
					subselector.addKey(new RangeIndirectKey(offset, lowerBound, upperBound));
				}
				else if(token.getType() == Token.Type.DOT || token.getType() == Token.Type.SLASH
						|| token.getType() == Token.Type.AT) {
					Selector selector = parseSelector();
					subselector.addKey(new SelectorIndirectKey(lowerBound, selector));
				}
				else
					subselector.addKey(new ValueIndirectKey(lowerBound));
				for(;;) {
					more("',' or ')'");
					if(token.getType() == Token.Type.COMMA) {
						next();
						more("indirect key");
						lowerBound = parseValue();
						offset = lowerBound.getOffset();
						more("':', selector, ',' or ')'");
						if(token.getType() == Token.Type.COLON) {
							offset = token.getOffset();
							next();
							SimpleValue upperBound = parseValue();
							subselector.addKey(new RangeIndirectKey(offset, lowerBound, upperBound));
						}
						else if(token.getType() == Token.Type.DOT || token.getType() == Token.Type.SLASH
								|| token.getType() == Token.Type.AT) {
							Selector selector = parseSelector();
							subselector.addKey(new SelectorIndirectKey(lowerBound, selector));
						}
						else
							subselector.addKey(new ValueIndirectKey(lowerBound));
					}
					else if(token.getType() == Token.Type.RIGHT_ROUND) {
						next();
						break;
					}
					else
						unexpected("',' or ')'");
				}
				break;
			default:
				unexpected("indirect key");
				return null;
		}
		return subselector;
	}

	private ByValueSubselector parseByValueSubselector() throws CompilationException {
		int ssOffset = token.getOffset();
		next();
		more("'==', '!=', 'lt', 'le', 'gt', 'ge' or name");
		switch(token.getType()) {
			case EQUAL:
			case UNEQUAL:
				{
					EqualityExpression.Operator operator;
					switch(token.getType()) {
						case EQUAL:
							operator = EqualityExpression.Operator.EQUAL;
							break;
						case UNEQUAL:
							operator = EqualityExpression.Operator.UNEQUAL;
							break;
						default:
							throw new Error("Assertion failed");
					}
					next();
					return new ByEqualValueSubselector(ssOffset, operator, parseValue());
				}
			case LT:
			case LE:
			case GT:
			case GE:
				{
					InequalityExpression.Operator operator;
					switch(token.getType()) {
						case LT:
							operator = InequalityExpression.Operator.LESS_THAN;
							break;
						case LE:
							operator = InequalityExpression.Operator.LESS_EQUAL;
							break;
						case GT:
							operator = InequalityExpression.Operator.GREATER_THAN;
							break;
						case GE:
							operator = InequalityExpression.Operator.GREATER_EQUAL;
							break;
						default:
							throw new Error("Assertion failed");
					}
					next();
					return new ByUnequalValueSubselector(ssOffset, operator, parseValue());
				}
			case NAME:
				{
					String name = token.getText();
					next();
					return new ByLetValueSubselector(ssOffset, name, parseSelectedValue());
				}
			default:
				unexpected("'==', '!=', 'lt', 'le', 'gt', 'ge' or name");
				return null;
		}
	}

	private ComplexValue parseComplexValue() throws CompilationException {
		return parseLogicalDisjunction();
	}

	private ComplexValue parseLogicalDisjunction() throws CompilationException {
		ComplexValue left = parseLogicalConjunction();
		while(token != null && token.getType() == Token.Type.LOG_OR) {
			int offset = token.getOffset();
			next();
			ComplexValue right = parseLogicalConjunction();
			left = new BinaryLogicalExpression(offset, left, BinaryLogicalExpression.Operator.DISJUNCTION, right);
		}
		return left;
	}

	private ComplexValue parseLogicalConjunction() throws CompilationException {
		ComplexValue left = parseBitwiseDisjunction();
		while(token != null && token.getType() == Token.Type.LOG_AND) {
			int offset = token.getOffset();
			next();
			ComplexValue right = parseBitwiseDisjunction();
			left = new BinaryLogicalExpression(offset, left, BinaryLogicalExpression.Operator.CONJUNCTION, right);
		}
		return left;
	}

	private ComplexValue parseBitwiseDisjunction() throws CompilationException {
		ComplexValue left = parseBitwiseXor();
		while(token != null && token.getType() == Token.Type.BIT_OR) {
			int offset = token.getOffset();
			next();
			ComplexValue right = parseBitwiseXor();
			left = new BinaryBitwiseExpression(offset, left, BinaryBitwiseExpression.Operator.DISJUNCTION, right);
		}
		return left;
	}

	private ComplexValue parseBitwiseXor() throws CompilationException {
		ComplexValue left = parseBitwiseConjunction();
		while(token != null && token.getType() == Token.Type.BIT_XOR) {
			int offset = token.getOffset();
			next();
			ComplexValue right = parseBitwiseConjunction();
			left = new BinaryBitwiseExpression(offset, left, BinaryBitwiseExpression.Operator.XOR, right);
		}
		return left;
	}

	private ComplexValue parseBitwiseConjunction() throws CompilationException {
		ComplexValue left = parseEquality();
		while(token != null && token.getType() == Token.Type.BIT_AND) {
			int offset = token.getOffset();
			next();
			ComplexValue right = parseEquality();
			left = new BinaryBitwiseExpression(offset, left, BinaryBitwiseExpression.Operator.CONJUNCTION, right);
		}
		return left;
	}

	private ComplexValue parseEquality() throws CompilationException {
		ComplexValue left = parseInequality();
		while(token != null) {
			int offset = token.getOffset();
			ComplexValue right;
			switch(token.getType()) {
				case EQUAL:
					next();
					right = parseInequality();
					left = new EqualityExpression(offset, left, EqualityExpression.Operator.EQUAL, right);
					break;
				case UNEQUAL:
					next();
					right = parseInequality();
					left = new EqualityExpression(offset, left, EqualityExpression.Operator.UNEQUAL, right);
					break;
				default:
					return left;
			}
		}
		return left;
	}

	private ComplexValue parseInequality() throws CompilationException {
		ComplexValue left = parseSum();
		while(token != null) {
			int offset = token.getOffset();
			ComplexValue right;
			switch(token.getType()) {
				case LT:
					next();
					right = parseSum();
					left = new InequalityExpression(offset, left, InequalityExpression.Operator.LESS_THAN, right);
					break;
				case LE:
					next();
					right = parseSum();
					left = new InequalityExpression(offset, left, InequalityExpression.Operator.LESS_EQUAL, right);
					break;
				case GT:
					next();
					right = parseSum();
					left = new InequalityExpression(offset, left, InequalityExpression.Operator.GREATER_THAN, right);
					break;
				case GE:
					next();
					right = parseSum();
					left = new InequalityExpression(offset, left, InequalityExpression.Operator.GREATER_EQUAL, right);
					break;
				default:
					return left;
			}
		}
		return left;
	}

	private ComplexValue parseSum() throws CompilationException {
		ComplexValue left = parseProduct();
		while(token != null) {
			int offset = token.getOffset();
			ComplexValue right;
			switch(token.getType()) {
				case PLUS:
					next();
					right = parseProduct();
					left = new BinaryArithmeticExpression(offset,
							left, BinaryArithmeticExpression.Operator.ADD, right);
					break;
				case MINUS:
					next();
					right = parseProduct();
					left = new BinaryArithmeticExpression(offset,
							left, BinaryArithmeticExpression.Operator.SUBTRACT, right);
					break;
				default:
					return left;
			}
		}
		return left;
	}

	private ComplexValue parseProduct() throws CompilationException {
		ComplexValue left = parsePrefix();
		while(token != null) {
			int offset = token.getOffset();
			ComplexValue right;
			switch(token.getType()) {
				case STAR:
					next();
					right = parsePrefix();
					left = new BinaryArithmeticExpression(offset,
							left, BinaryArithmeticExpression.Operator.MULTIPLY, right);
					break;
				case DIV:
					next();
					right = parsePrefix();
					left = new BinaryArithmeticExpression(offset,
							left, BinaryArithmeticExpression.Operator.DIVIDE, right);
					break;
				case PERCENT:
					next();
					right = parsePrefix();
					left = new BinaryArithmeticExpression(offset,
							left, BinaryArithmeticExpression.Operator.MODULO, right);
					break;
				default:
					return left;
			}
		}
		return left;
	}

	private ComplexValue parsePrefix() throws CompilationException {
		if(token == null)
			return parsePostfix();
		int offset = token.getOffset();
		PrefixExpression.Operator operator;
		switch(token.getType()) {
			case PLUS:
				operator = PrefixExpression.Operator.POSITIVE;
				break;
			case MINUS:
				operator = PrefixExpression.Operator.NEGATIVE;
				break;
			case NOT:
				operator = PrefixExpression.Operator.NOT;
				break;
			case TILDE:
				operator = PrefixExpression.Operator.COMPLEMENT;
				break;
			default:
				return parsePostfix();
		}
		return new PrefixExpression(offset, operator, parsePrefix());
	}

	private ComplexValue parsePostfix() throws CompilationException {
		ComplexValue result = parseUntransformedValue();
		while(token != null) {
			int offset = token.getOffset();
			switch(token.getType()) {
				case LEFT_ROUND:
					{
						CallExpression call = new CallExpression(offset, result);
						parseCall(call);
						result = call;
					}
					break;
				case LEFT_CURLY:
					{
						TransformationExpression transformation = new TransformationExpression(offset, result);
						parseTransformation(transformation);
						result = transformation;
					}
					break;
				default:
					return result;
			}
		}
		return result;
	}

	private void parseCall(CallExpression call) throws CompilationException {
		next();
		more("complex value or ')'");
		if(token.getType() == Token.Type.RIGHT_ROUND) {
			next();
			return;
		}
		for(;;) {
			call.addArgument(parseComplexValue());
			more("',' or ')'");
			switch(token.getType()) {
				case COMMA:
					next();
					break;
				case RIGHT_ROUND:
					next();
					return;
				default:
					more("',' or ')'");
			}
		}
	}

	private void parseTransformation(TransformationExpression transformation) throws CompilationException {
		next();
		for(;;) {
			transformation.addTransform(parsePlainTransform());
			more("',' or '}'");
			switch(token.getType()) {
				case COMMA:
					next();
					break;
				case RIGHT_CURLY:
					next();
					return;
				default:
					unexpected("',' or '}'");
			}
		}
	}

	private UntransformedValue parseUntransformedValue() throws CompilationException {
		more("untransformed value");
		switch(token.getType()) {
			case NAME:
				{
					NameValue name = new NameValue(token.getOffset(), token.getText());
					next();
					return name;
				}
			case STRING:
			case INT:
			case FLOAT:
			case THIS:
			case LEFT_CURLY:
			case LEFT_SQUARE:
			case LEFT_ANGLE:
				return parseNonNameValue();
			default:
				unexpected("untransformed value");
				return null;
		}
	}

	private SimpleValue parseValue() throws CompilationException {
		UntransformedValue untransformed = parseUntransformedValue();
		if(token == null || token.getType() != Token.Type.LEFT_CURLY)
			return untransformed;
		TransformationExpression transformation = new TransformationExpression(token.getOffset(), untransformed);
		parseTransformation(transformation);
		return transformation;
	}

	private NonNameValue parseNonNameValue() throws CompilationException {
		more("non-name value");
		switch(token.getType()) {
			case STRING:
				{
					StringLiteral literal = new StringLiteral(token.getOffset(), token.getText());
					next();
					return literal;
				}
			case INT:
				{
					IntLiteral literal = new IntLiteral(token.getOffset(), Long.parseLong(token.getText()));
					next();
					return literal;
				}
			case FLOAT:
				{
					FloatLiteral literal = new FloatLiteral(token.getOffset(), Double.parseDouble(token.getText()));
					next();
					return literal;
				}
			case THIS:
				{
					int offset = token.getOffset(), level = -1;
					do {
						++level;
						next();
					} while(token != null && token.getType() == Token.Type.THIS);
					return new ThisValue(offset, level);
				}
			case LEFT_SQUARE:
				return new ConstructionValue(parseArrayConstruction());
			case LEFT_CURLY:
				return new ConstructionValue(parseObjectConstruction());
			case LEFT_ANGLE:
				return parseSelectedValue();
			default:
				unexpected("non-name value");
				return null;
		}
	}

	private SelectedValue parseSelectedValue() throws CompilationException {
		more("'<'");
		if(token.getType() != Token.Type.LEFT_ANGLE)
			unexpected("'<'");
		int offset = token.getOffset();
		next();
		ComplexValue value = parseComplexValue();
		more("selector or '>'");
		Selector selector = null;
		switch(token.getType()) {
			case DOT:
			case SLASH:
			case AT:
				selector = parseSelector();
				more("'>'");
				if(token.getType() != Token.Type.RIGHT_ANGLE)
					unexpected("'>'");
			case RIGHT_ANGLE:
				next();
				return new SelectedValue(offset, value, selector);
			default:
				unexpected("selector or '>'");
				return null;
		}
	}

}
