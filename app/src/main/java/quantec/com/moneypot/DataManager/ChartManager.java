package quantec.com.moneypot.DataManager;

import java.util.List;

import quantec.com.moneypot.DataModel.dModel.ModelTransChartList;
import quantec.com.moneypot.DataModel.dModel.TransChartList;

public class ChartManager {

    private static ChartManager _Instance;

    public static ChartManager get_Instance() {
        if(_Instance == null)
        {
            _Instance = new ChartManager();
        }
        return _Instance;
    }

    List<ModelTransChartList> modelTransChartLists;

    public List<ModelTransChartList> getTransChartLists() {
        return modelTransChartLists;
    }

    public void setTransChartLists(List<ModelTransChartList> modelTransChartLists) {
        this.modelTransChartLists = modelTransChartLists;
    }

//    private static ChartManager _Instance;
//
//    public static ChartManager get_Instance() {
//        if(_Instance == null)
//        {
//            _Instance = new ChartManager();
//        }
//        return _Instance;
//    }
//
//    List<TransChartList> transChartLists;
//
//    public List<TransChartList> getTransChartLists() {
//        return transChartLists;
//    }
//
//    public void setTransChartLists(List<TransChartList> transChartLists) {
//        this.transChartLists = transChartLists;
//    }
}
