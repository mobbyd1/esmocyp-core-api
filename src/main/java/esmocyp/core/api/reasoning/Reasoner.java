package esmocyp.core.api.reasoning;

import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import eu.larkc.csparql.common.utils.CsparqlUtils;
import eu.larkc.csparql.common.utils.ReasonerChainingType;
import eu.larkc.csparql.core.engine.CsparqlEngine;
import eu.larkc.csparql.core.engine.CsparqlEngineImpl;
import eu.larkc.csparql.core.engine.CsparqlQueryResultProxy;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class Reasoner extends RdfStream {

    private CsparqlEngine engine;

    private String query;
    private String namedModel;
    private String aBox;
    private String tBox;

    private Callback callback;

    public Reasoner(
            String query,
            String streamingUrl,
            String namedModel,
            String aBox,
            String tBox,
            Callback callback ) {

        super(streamingUrl);

        this.query = query;
        this.namedModel = namedModel;
        this.aBox = aBox;
        this.tBox = tBox;

        this.callback = callback;
    }

    public void init() throws Exception {
        engine = new CsparqlEngineImpl();

        //Initialize the engine instance
        //The initialization creates the static engine (SPARQL) and the stream engine (CEP)
        engine.initialize();

        File aboxFile = File.createTempFile("esmocyp", "abox");
        FileUtils.writeByteArrayToFile( aboxFile, aBox.getBytes() );

        String serializedaBox = CsparqlUtils.serializeRDFFile(aboxFile.getAbsolutePath());
        engine.putStaticNamedModel( namedModel, serializedaBox);

        engine.registerStream( this );
        CsparqlQueryResultProxy csparqlQueryResultProxy = engine.registerQuery(query, false);

        csparqlQueryResultProxy.addObserver( callback );

        File tBoxFile = File.createTempFile("esmocyp", "tbox");
        FileUtils.writeByteArrayToFile( tBoxFile, tBox.getBytes() );

        String serializedtBox = CsparqlUtils.serializeRDFFile(tBoxFile.getAbsolutePath());

        engine.updateReasoner(
                csparqlQueryResultProxy.getSparqlQueryId()
                , ""
                , ReasonerChainingType.BACKWARD
                , serializedtBox );
    }

    public void destroy() {
        engine.destroy();
    }

    public void insertTriple(String subject, String predicate, String object) {
        RdfQuadruple rdfQuadruple = new RdfQuadruple(
                subject
                , predicate
                , object
                , System.currentTimeMillis());

        this.put( rdfQuadruple );
    }
}
