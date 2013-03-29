package kg.apc.jmeter.reporters;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 *
 * @author undera
 */
public class LoadosophiaAggregator {

    private static final Logger log = LoggingManager.getLoggerForClass();
    private SortedMap<Long, List<SampleResult>> buffer = new TreeMap<Long, List<SampleResult>>();
    private final long SEND_SECONDS = 5;
    private long lastTime = 0;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public void addSample(SampleResult res) {
        if (log.isDebugEnabled()) {
            log.debug("Got sample to process: " + res);
        }

        Long time = res.getEndTime() / 1000;
        if (!buffer.containsKey(time)) {
            // we need to create new sec list
            if (time < lastTime) {
                // a problem with times sequence - taking last available
                Iterator<Long> it = buffer.keySet().iterator();
                while (it.hasNext()) {
                    time = it.next();
                }
            }
            buffer.put(time, new LinkedList<SampleResult>());
        }
        lastTime = time;
        buffer.get(time).add(res);
    }

    public boolean haveDataToSend() {
        return buffer.size() > SEND_SECONDS + 1;
    }

    public JSONArray getDataToSend() {
        JSONArray data = new JSONArray();
        Iterator<Long> it = buffer.keySet().iterator();
        int cnt = 0;
        while (cnt < SEND_SECONDS && it.hasNext()) {
            Long sec = it.next();
            List<SampleResult> raw = buffer.get(sec);
            data.add(getAggregateSecond(raw));
            it.remove();
            cnt++;
        }
        return data;
    }

    private JSONObject getAggregateSecond(List<SampleResult> raw) {
        /*
         "rc": item.http_codes,
         "net": item.net_codes
         */
        JSONObject result = new JSONObject();
        Date ts = new Date(raw.iterator().next().getEndTime());
        result.put("ts", format.format(ts));

        int threads = 0;
        int avg_rt = 0;
        Long[] rtimes = new Long[raw.size()];
        int cnt = 0;
        for (Iterator<SampleResult> it = raw.iterator(); it.hasNext();) {
            SampleResult res = it.next();
            threads += res.getAllThreads();
            avg_rt += res.getTime();
            rtimes[cnt] = new Long(res.getTime());
            cnt++;
        }
        result.put("rps", cnt);
        result.put("threads", threads / cnt);
        result.put("avg_rt", avg_rt / cnt);
        result.put("quantiles", getQuantilesJSON(rtimes));
        result.put("planned_rps", 0); // JMeter has no such feature like Yandex.Tank
        return result;
    }

    public static JSONObject getQuantilesJSON(Long[] rtimes) {
        JSONObject result = new JSONObject();
        Arrays.sort(rtimes);

        double[] quantiles = {0.25, 0.50, 0.75, 0.80, 0.90, 0.95, 0.98, 0.99, 1.00};

        Stack<Long> timings = new Stack();
        timings.addAll(Arrays.asList(rtimes));
        double level = 1.0;
        long timing = 0;
        for (int qn = quantiles.length - 1; qn >= 0; qn--) {
            double quan = quantiles[qn];
            while (level >= quan) {
                timing = timings.pop();
                level -= 1.0 / rtimes.length;
            }
            result.element(String.valueOf(quan * 100), timing);
        }

        return result;
    }
}
