package esmocyp.core.api;

import esmocyp.core.api.reasoning.Reasoner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsmocypeCoreApiApplicationTests {

	private static final String BASE_URL = "urn:x-hp:eg/";

	@Test
	public void contextLoads() throws Exception {
		String query = getQuery();
		String namedModel = "http://streamreasoning.org/hospital-data";
		String streamingURL = "http://streamreasoning.org/streams/hospital";
		String aBox = getABox();
		String tBox = getTBox();

		final Reasoner reasoner = new Reasoner(query, streamingURL, namedModel, aBox, tBox);
		reasoner.init();

		stream( reasoner );
	}

	public void stream( Reasoner reasoner ) throws InterruptedException {
        for( ; ; ) {
            String subject1 = BASE_URL + "smartphoneDoRuhan";
            String predicate1 = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
            String object1 = BASE_URL + "NaoEncontrado";

            reasoner.insertTriple( subject1, predicate1, object1 );

            String subject2 = BASE_URL + "saladeEspera1";
            String predicate2 = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
            String object2 = BASE_URL + "SalaCheia";

            reasoner.insertTriple( subject2, predicate2, object2 );

            Thread.sleep( 500 );
        }
    }

	private String getQuery() {
		return "REGISTER QUERY staticKnowledge AS " +
				"PREFIX :<urn:x-hp:eg/> " +
				"SELECT ?s ?s1 " +
				"FROM STREAM <http://streamreasoning.org/streams/hospital> [RANGE 5s STEP 1s] " +
				"FROM <http://streamreasoning.org/hospital-data> " +
				"WHERE { " +
				"?s :hasConnectionTo ?s1 . " +
				"?s a :SalaComGargalo . " +
				"} ";
	}

	private String getTBox() {
		return "<?xml version=\"1.0\"?>\n" +
				"\n" +
				"<!DOCTYPE rdf:RDF [\n" +
				"    <!ENTITY eg   'urn:x-hp:eg/'>\n" +
				"    <!ENTITY rdf  'http://www.w3.org/1999/02/22-rdf-syntax-ns#'>\n" +
				"    <!ENTITY rdfs 'http://www.w3.org/2000/01/rdf-schema#'>\n" +
				"    <!ENTITY xsd  'http://www.w3.org/2001/XMLSchema#'>\n" +
				"    <!ENTITY owl  \"http://www.w3.org/2002/07/owl#\" >\n" +
				"]>\n" +
				"\n" +
				"<rdf:RDF\n" +
				"    xmlns:rdf=\"&rdf;\"\n" +
				"    xmlns:rdfs=\"&rdfs;\"\n" +
				"    xmlns:xsd=\"&xsd;\"\n" +
				"    xmlns:owl=\"&owl;\"\n" +
				"    xml:base=\"urn:x-hp:eg/\"\n" +
				"    xmlns:eg=\"&eg;\" xmlns=\"&eg;\">\n" +
				"\n" +
				"    <owl:Class rdf:about=\"&eg;Pessoa\" />\n" +
				"    <owl:Class rdf:about=\"&eg;Sala\" />\n" +
				"    <owl:Class rdf:about=\"&eg;Smartphone\" />\n" +
				"\n" +
				"    <owl:Class rdf:about=\"&eg;Medico\">\n" +
				"        <rdfs:subClassOf rdf:resource=\"&eg;Pessoa\" />\n" +
				"    </owl:Class>\n" +
				"\n" +
				"    <owl:ObjectProperty rdf:about=\"&eg;temSmartphone\">\n" +
				"        <rdfs:domain rdf:resource=\"&eg;Pessoa\" />\n" +
				"        <rdfs:range rdf:resource=\"&eg;Smartphone\" />\n" +
				"    </owl:ObjectProperty>\n" +
				"\n" +
				"    <owl:ObjectProperty rdf:about=\"&eg;temSala\">\n" +
				"        <rdfs:domain rdf:resource=\"&eg;Medico\" />\n" +
				"        <rdfs:range rdf:resource=\"&eg;Sala\" />\n" +
				"    </owl:ObjectProperty>\n" +
				"\n" +
				"    <owl:ObjectProperty rdf:about=\"&eg;temMedico\">\n" +
				"        <rdfs:domain rdf:resource=\"&eg;Sala\" />\n" +
				"        <rdfs:range rdf:resource=\"&eg;Medico\" />\n" +
				"    </owl:ObjectProperty>\n" +
				"\n" +
				"    <owl:ObjectProperty rdf:about=\"&eg;hasConnectionTo\">\n" +
				"        <rdfs:domain rdf:resource=\"&eg;Sala\" />\n" +
				"        <rdfs:range rdf:resource=\"&eg;Sala\" />\n" +
				"    </owl:ObjectProperty>\n" +
				"\n" +
				"    <owl:Class rdf:about=\"&eg;NaoEncontrado\">\n" +
				"        <rdfs:subClassOf rdf:resource=\"&eg;Smartphone\" />\n" +
				"    </owl:Class>\n" +
				"\n" +
				"    <owl:Class rdf:about=\"eg;SalaCheia\">\n" +
				"        <rdfs:subClassOf rdf:resource=\"&eg;Sala\" />\n" +
				"    </owl:Class>\n" +
				"\n" +
				"    <owl:Class rdf:about=\"&eg;NaoVeioTrabalhar\">\n" +
				"        <owl:equivalentClass>\n" +
				"            <rdf:Description>\n" +
				"                <owl:intersectionOf rdf:parseType=\"Collection\">\n" +
				"                    <owl:Restriction>\n" +
				"                        <owl:onProperty rdf:resource=\"&eg;temSmartphone\" />\n" +
				"                        <owl:someValuesFrom rdf:resource=\"&eg;NaoEncontrado\" />\n" +
				"                    </owl:Restriction>\n" +
				"                    <owl:Class rdf:about=\"&eg;Medico\" />\n" +
				"                </owl:intersectionOf>\n" +
				"            </rdf:Description>\n" +
				"        </owl:equivalentClass>\n" +
				"    </owl:Class>\n" +
				"\n" +
				"    <owl:Class rdf:about=\"urn:x-hp:eg/SalaComGargalo\">\n" +
				"        <owl:equivalentClass>\n" +
				"            <owl:Class>\n" +
				"                <owl:intersectionOf rdf:parseType=\"Collection\">\n" +
				"                    <rdf:Description rdf:about=\"&eg;SalaCheia\" />\n" +
				"                    <owl:Restriction>\n" +
				"                        <owl:onProperty rdf:resource=\"&eg;hasConnectionTo\" />\n" +
				"                        <owl:someValuesFrom>\n" +
				"                            <owl:Class>\n" +
				"                                <owl:intersectionOf rdf:parseType=\"Collection\">\n" +
				"                                    <rdf:Description rdf:about=\"&eg;Sala\"/>\n" +
				"                                    <owl:Restriction>\n" +
				"                                        <owl:onProperty rdf:resource=\"&eg;temMedico\"/>\n" +
				"                                        <owl:someValuesFrom rdf:resource=\"&eg;NaoVeioTrabalhar\"/>\n" +
				"                                    </owl:Restriction>\n" +
				"                                </owl:intersectionOf>\n" +
				"                            </owl:Class>\n" +
				"                        </owl:someValuesFrom>\n" +
				"                    </owl:Restriction>\n" +
				"                </owl:intersectionOf>\n" +
				"            </owl:Class>\n" +
				"        </owl:equivalentClass>\n" +
				"    </owl:Class>\n" +
				"\n" +
				"</rdf:RDF>";
	}

	private String getABox(){
		return "<?xml version=\"1.0\"?>\n" +
				"\n" +
				"<!DOCTYPE rdf:RDF [\n" +
				"    <!ENTITY eg   'urn:x-hp:eg/'>\n" +
				"    <!ENTITY rdf  'http://www.w3.org/1999/02/22-rdf-syntax-ns#'>\n" +
				"    <!ENTITY rdfs 'http://www.w3.org/2000/01/rdf-schema#'>\n" +
				"    <!ENTITY xsd  'http://www.w3.org/2001/XMLSchema#'>\n" +
				"    <!ENTITY owl  \"http://www.w3.org/2002/07/owl#\" >\n" +
				"]>\n" +
				"\n" +
				"<rdf:RDF\n" +
				"    xmlns:rdf=\"&rdf;\"\n" +
				"    xmlns:rdfs=\"&rdfs;\"\n" +
				"    xmlns:xsd=\"&xsd;\"\n" +
				"    xmlns:owl=\"&owl;\"\n" +
				"    xml:base=\"urn:x-hp:eg/\"\n" +
				"    xmlns=\"&eg;\">\n" +
				"\n" +
				"  <Medico rdf:about=\"&eg;Ruhan\">\n" +
				"      <temSmartphone>\n" +
				"          <Smartphone rdf:about=\"&eg;smartphoneDoRuhan\">\n" +
				"          </Smartphone>\n" +
				"      </temSmartphone>\n" +
				"      <temSala>\n" +
				"        <Sala rdf:about=\"&eg;salaDoRuhan\">\n" +
				"        </Sala>\n" +
				"      </temSala>\n" +
				"  </Medico>\n" +
				"\n" +
				"  <Sala rdf:about=\"&eg;salaDoRuhan\">\n" +
				"    <temMedico>\n" +
				"        <Medico rdf:about=\"&eg;Ruhan\" />\n" +
				"    </temMedico>\n" +
				"  </Sala>\n" +
				"\n" +
				"  <Sala rdf:about=\"&eg;saladeEspera1\">\n" +
				"    <hasConnectionTo>\n" +
				"        <Sala rdf:about=\"&eg;salaDoRuhan\" />\n" +
				"    </hasConnectionTo>\n" +
				"  </Sala>\n" +
				"\n" +
				"</rdf:RDF>";
	}
}
