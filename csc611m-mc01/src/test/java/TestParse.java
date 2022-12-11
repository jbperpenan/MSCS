import org.jbp.csc611m.mc01.entities.Crew;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestParse {

    @Test
    public void test () {
        String content = "Dangerous Minds: Directed by John N. Smith. With Michelle Pfeiffer, George Dzundza, Courtney B. Vance, Robin Bartlett. An ex-Marine turned teacher struggles to connect with her students in an inner city school.";

        //String content = "Free Willy 2: The Adventure Home: Directed by Dwight H. Little. With Jason James Richter, Francis Capra, Mary Kate Schellhardt, August Schellenberg. Teenager Jesse becomes reunited with Willy two years after the whale's jump to freedom as the teenager tries to rescue the killer whale and other orcas from an oil spill.";
        //String content = "Heidi Fleiss: Hollywood Madam: Directed by Nick Broomfield. With Nick Broomfield, Nina Xining Zuo, Madam Alex, Corinne Bohrer. A documentary crew from the BBC arrives in L.A. intent on interviewing Heidi Fleiss, a year after her arrest for running a brothel but before her trial. Several months elapse before the interview, so the crew searches for anyone who'll talk about the young woman. Two people have a lot to say to the camera: a retired madam named Alex for whom Fleiss once worked and Fleiss's one-time boyfriend, Ivan Nagy, who introduced her to Alex. Alex and Nagy don't like each other, so the crew shuttles between them with \"she said\" and \"he said.\" When they finally interview Fleiss, they spend their time reciting what Alex and Nagy have had to say and asking her reaction.";
        String director = getDirector(content);
        String casts = getCasts(content);

        Crew crew = new Crew(director,casts,null);
        //System.out.println("->"+ crew.toString());

        assertTrue(director.equals("John N. Smith"));
        assertTrue(casts.equals("Michelle Pfeiffer| George Dzundza| Courtney B. Vance| Robin Bartlett"));

        //assertTrue(director.equals("Dwight H. Little"));
        //assertTrue(casts.equals("Jason James Richter| Francis Capra| Mary Kate Schellhardt| August Schellenberg"));

        //assertTrue(director.equals("Nick Broomfield"));
        //assertTrue(casts.equals("Nick Broomfield| Nina Xining Zuo| Madam Alex| Corinne Bohrer"));
    }

    private String getDirector(String content){
        int d1 = content.indexOf("Directed by");
        int d2 = content.indexOf(". With ");

        String director = content.substring(d1+12, d2).trim().replace(',','|');

        return director;
    }

    private String getCasts(String content){
        int d1 = content.indexOf(". With ");

        String cs = content.substring(d1+7, content.length());
        //System.out.println(cs);

        //int lastIndex = cs.indexOf(".");
        //String casts = content.substring(d1+7, content.lastIndexOf(". "));
        String casts = cs.substring(0, cs.indexOf("."));

        casts = casts.trim().replace(',','|');
        //System.out.println(casts);

        return casts;
    }
}
