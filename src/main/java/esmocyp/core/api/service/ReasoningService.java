package esmocyp.core.api.service;

import esmocyp.core.api.ApplicationContextUtil;
import esmocyp.core.api.dto.ReasonerDTO;
import esmocyp.core.api.dto.StreamDTO;
import esmocyp.core.api.model.User;
import esmocyp.core.api.reasoning.Callback;
import esmocyp.core.api.reasoning.Reasoner;
import esmocyp.core.api.service.exception.ReasoningServiceException;
import eu.larkc.csparql.common.utils.CsparqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReasoningService {

    @Autowired
    private ApplicationContextUtil applicationContextUtil;
    
    private Map<User, Reasoner> reasoners = new ConcurrentHashMap<>();

    public void create(User user, ReasonerDTO dto) throws ReasoningServiceException {

        try {
            String query = dto.getQuery();
            String streamingURL = dto.getStreamingURL();
            String namedModel = dto.getNamedModel();
            ClassLoader classLoader = ReasoningService.class.getClassLoader();

            File esmocypData = new File(classLoader.getResource("teste-temperatura-humidade-corredor-andar-isnear/1-andar-1-corredor/esmocyp-data.rdf").getFile());
            File ontology = new File(classLoader.getResource("teste-temperatura-humidade-corredor-andar-isnear/esmocyp-temperature-corredor-andar.owl").getFile());

            String aBox = CsparqlUtils.serializeRDFFile(esmocypData.getAbsolutePath());
            String tBox = CsparqlUtils.serializeRDFFile(ontology.getAbsolutePath());

            Callback callback = applicationContextUtil.getApplicationContext().getBean(Callback.class);
            callback.setUuids( dto.getUuids() );

            Reasoner reasoner = new Reasoner(
                    query
                    , streamingURL
                    , namedModel
                    , aBox
                    , tBox
                    , callback);

            reasoners.put(user, reasoner);
            reasoner.init();

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss.sssss");
            Date now = new Date();

            System.out.println("------- Reasoner init at SystemTime=[" + dateFormat.format(now) + "]--------");

        } catch (Exception e) {
            throw new ReasoningServiceException();
        }
    }

    public void update(User user, ReasonerDTO dto) throws ReasoningServiceException {
        Reasoner reasoner = reasoners.get(user);
        if( reasoner != null ) {
            reasoner.destroy();
            create( user, dto );
        }
    }

    public void destroy(User user) {
        Reasoner reasoner = reasoners.get(user);
        if( reasoner != null ) {
            reasoner.destroy();
        }
    }

    public void insertTriple(User user, StreamDTO dto) {
        Reasoner reasoner = reasoners.get(user);

        if( reasoner != null ) {
            String subject = dto.getSubject();
            String predicate = dto.getPredicate();
            String object = dto.getObject();

            reasoner.insertTriple( subject, predicate, object );
        }
    }
}
