package esmocyp.core.api.reasoning;

import esmocyp.core.api.ApplicationContextUtil;
import esmocyp.core.api.broadcast.ReasoningBroadcaster;
import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.RDFTuple;
import eu.larkc.csparql.core.ResultFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Scope("prototype")
public class Callback extends ResultFormatter {

    @Autowired
    private ApplicationContextUtil applicationContextUtil;

    private ReasoningBroadcaster broadcaster;

    public Callback() {
        //broadcaster = applicationContextUtil.getApplicationContext().getBean( ReasoningBroadcaster.class );
    }

    public void setUuids( List<String> uuids ) {
       // this.broadcaster.setUuids( uuids );
    }

    @Override
    public void update(Observable o, Object arg) {
        RDFTable q = (RDFTable)arg;
        System.out.println();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss.sssss");
        Date now = new Date();

        System.out.println("-------" + q.size() + " results at SystemTime=[" + dateFormat.format(now) + "]--------");
        Iterator var4 = q.iterator();

        while(var4.hasNext()) {
            RDFTuple t = (RDFTuple)var4.next();
            System.out.println(t.toString());

            //broadcaster.broadcast( t.toString() );
        }

        System.out.println();
    }
}
