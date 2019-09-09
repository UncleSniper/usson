package org.unclesniper.json.j8;

import org.unclesniper.json.OMGWereDoomedError;

public class IntBound implements IntP {

	private int threshold;

	private OrderRelation relation;

	public IntBound(int threshold, OrderRelation relation) {
		this.threshold = threshold;
		this.relation = relation;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public OrderRelation getRelation() {
		return relation;
	}

	public void setRelation(OrderRelation relation) {
		this.relation = relation;
	}

	@Override
	public boolean testInt(int value) {
		switch(relation) {
			case LT:
				return value < threshold;
			case LE:
				return value <= threshold;
			case EQ:
				return value == threshold;
			case GE:
				return value >= threshold;
			case GT:
				return value > threshold;
			default:
				throw new OMGWereDoomedError("Unrecognized order relation: " + relation.name());
		}
	}

}
