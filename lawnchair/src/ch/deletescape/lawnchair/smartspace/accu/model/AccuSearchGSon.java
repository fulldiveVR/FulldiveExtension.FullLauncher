
package ch.deletescape.lawnchair.smartspace.accu.model;

import ch.deletescape.lawnchair.smartspace.accu.model.sub.AccuLocationGSon;
import java.util.List;

public class AccuSearchGSon extends GSonBase {
    List<AccuLocationGSon> cities;

    public List<AccuLocationGSon> getCities() {
        return this.cities;
    }

    public AccuSearchGSon setCities(List<AccuLocationGSon> cities2) {
        this.cities = cities2;
        return this;
    }
}
