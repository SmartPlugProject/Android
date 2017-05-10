package eduardo.alexsander.com.smartplugandroid;

import java.util.Date;
import java.util.List;

/**
 * Created by 212571132 on 5/8/2017.
 */

public class Sensor {
    private String _id;
    private String name;
    private String device;
    private List<Value> values;

    public Sensor() {
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public class Value {
        private Date timestamp;
        private float voltage;
        private float current;

        public Value() {
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public float getVoltage() {
            return voltage;
        }

        public void setVoltage(float voltage) {
            this.voltage = voltage;
        }

        public float getCurrent() {
            return current;
        }

        public void setCurrent(float current) {
            this.current = current;
        }
    }
}
