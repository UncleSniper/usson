package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;

public interface StaticJSON {

	void jsonize(JSONSink sink, int version) throws IOException;

}
