package cleancode;

class CsvWriter {

    public void writeLine() {
    }

    public void writeCell(String value) {
    }
}

class SomeOtherClass {
    public void writeFooter(CsvWriter writer) {
        writer.writeCell("Generated with X SoftWare");
        writer.writeLine();
    }
}

class AnotherDistantClass {
    private final CsvWriter csvWriter;

    public AnotherDistantClass(CsvWriter csvWriter) {
        this.csvWriter = csvWriter;
    }
    public void writeHeader() {
        csvWriter.writeCell("Head1");
        csvWriter.writeCell("Head2");
        csvWriter.writeLine();
    }

}
