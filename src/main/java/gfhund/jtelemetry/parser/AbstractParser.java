package gfhund.jtelemetry.parser;

import java.util.ArrayList;
import gfhund.jtelemetry.data.Timing;
import java.io.File;

abstract class AbstractParser{
    abstract ArrayList<Timing> parse(File file);
}