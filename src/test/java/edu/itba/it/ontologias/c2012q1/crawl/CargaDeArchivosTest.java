package edu.itba.it.ontologias.c2012q1.crawl;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;

import bixo.datum.FetchedDatum;
import bixo.datum.ParsedDatum;
import bixo.datum.UrlStatus;
import cascading.scheme.SequenceFile;
import cascading.scheme.TextLine;
import cascading.tap.Hfs;
import cascading.tap.Tap;
import cascading.tuple.TupleEntry;
import cascading.tuple.TupleEntryIterator;

/**
 * TODO: Description of the class, Comments in english by default
 *
 *
 * @author Juan F. Codagnone
 * @since Apr 15, 2012
 */
public class CargaDeArchivosTest {
    private final JobConf conf = new JobConf();
    
    private final Tap crawldbTap;
    private final Tap contentTap;
    private final Tap parseTap;
    private final Tap statusTap;

    private CargaDeArchivosTest(final String curWorkingDirPath) {
        crawldbTap = new Hfs(new SequenceFile(CrawlDbDatum.FIELDS),
                new Path(curWorkingDirPath, "crawldb").toString());
        contentTap = new Hfs(new SequenceFile(FetchedDatum.FIELDS),
                new Path(curWorkingDirPath, "content").toString());
        parseTap = new Hfs(new SequenceFile(ParsedDatum.FIELDS),
                new Path(curWorkingDirPath, "parse").toString());
        statusTap = new Hfs(new TextLine(),
                new Path(curWorkingDirPath, "status").toString());
    }
    
    public void run() throws IOException {
        final TupleEntryIterator it = contentTap.openForRead(conf);
        while(it.hasNext()) {
            final TupleEntry t = it.next();
            final FetchedDatum datum = new FetchedDatum(t);
            System.out.println(new String(datum.getContentBytes()));
            System.out.println(datum);
        }    
    }
    
    public static void main(final String[] args) throws IOException {
        new CargaDeArchivosTest("/home/juan/cinenacional/47-20120414T014554/").run();
    }
}
