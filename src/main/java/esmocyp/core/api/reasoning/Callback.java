package esmocyp.core.api.reasoning;

import esmocyp.core.api.broadcast.ReasoningBroadcaster;
import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.RDFTuple;
import eu.larkc.csparql.core.ResultFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

@Component
@Scope("prototype")
public class Callback extends ResultFormatter {

    @Autowired
    private ApplicationContext applicationContext;

    private ReasoningBroadcaster broadcaster;

    public Callback() {
        broadcaster = applicationContext.getBean( ReasoningBroadcaster.class );
    }

    public void setUuids( List<String> uuids ) {
        this.broadcaster.setUuids( uuids );
    }

    @Override
    public void update(Observable o, Object arg) {
        RDFTable q = (RDFTable)arg;
        System.out.println();
        System.out.println("-------" + q.size() + " results at SystemTime=[" + System.currentTimeMillis() + "]--------");
        Iterator var4 = q.iterator();

        while(var4.hasNext()) {
            RDFTuple t = (RDFTuple)var4.next();
            System.out.println(t.toString());

            broadcaster.broadcast( t.toString() );
        }

        System.out.println();
    }
}
