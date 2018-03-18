package esmocyp.core.api.service;

import esmocyp.core.api.dto.ReasonerDTO;
import esmocyp.core.api.dto.StreamDTO;
import esmocyp.core.api.model.User;
import esmocyp.core.api.reasoning.Reasoner;
import esmocyp.core.api.service.exception.ReasoningServiceException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReasoningService {

    private Map<User, Reasoner> reasoners = new ConcurrentHashMap<>();

    public void create(User user, ReasonerDTO dto) throws ReasoningServiceException {
        String query = dto.getQuery();
        String streamingURL = dto.getStreamingURL();
        String namedModel = dto.getNamedModel();

        String aBox = dto.getABox();
        String tBox = dto.getTbox();

        final Reasoner reasoner = new Reasoner(query, streamingURL, namedModel, aBox, tBox);
        reasoners.put(user, reasoner);

        try {
            reasoner.init();
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
