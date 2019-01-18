package com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater;

        import java.util.List;
        import java.util.Map;

        import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwDetailsOther;
        import org.springframework.stereotype.Repository;

@Repository
public interface DgOwDetailsOtherMapper {
    int insert(DgOwDetailsOther record);

    int insertSelective(DgOwDetailsOther record);

    List<DgOwDetailsOther> selectByOwId(Map src);

    int insertBatch(List<DgOwDetailsOther> src);
}