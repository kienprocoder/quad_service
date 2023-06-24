package vn.itech.com.quad_service.payload.request;

import java.sql.Time;
import java.sql.Timestamp;

public class DepartmentRequest {

    private String name;

    private Timestamp createDate;

    private Timestamp updateDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }
}
