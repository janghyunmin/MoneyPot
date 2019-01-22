package quantec.com.moneypot.DataManager;

import java.util.List;

import quantec.com.moneypot.TransChartList;

public class ChartManager {
    private static ChartManager _Instance;

    public static ChartManager get_Instance() {
        if(_Instance == null)
        {
            _Instance = new ChartManager();
        }
        return _Instance;
    }

    List<TransChartList> transChartLists;

    public List<TransChartList> getTransChartLists() {
        return transChartLists;
    }

    public void setTransChartLists(List<TransChartList> transChartLists) {
        this.transChartLists = transChartLists;
    }
}
