package com.soft.mapp.basecenter.services;

import com.soft.mapp.basecenter.handler.PageModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IContext<M extends IDataModel, ID extends Serializable>
        extends IDoMainContext
{
    void add(M paramM);

    ID addAndReturn(M paramM, String paramString);

    long addList(List<M> paramList);

    List<ID> addListAndReturn(List<M> paramList, String paramString);

    long update(M paramM);

    long updateList(List<M> paramList);

    long delete(M paramM);

    long deleteByKey(ID paramID);

    long deleteList(List<M> paramList);

    void save(M paramM);

    void saveList(List<M> paramList);

    Optional<M> findOrNew(ID paramID);

    Optional<M> find(ID paramID);

    boolean exists(ID paramID);

    List<M> findAll(Iterable<ID> paramIterable);



    List<M> findTop1000(Map<String, Object> paramMap, String paramString1, String paramString2);

    PageModel<M> findListWithPage(Map<String, Object> paramMap, int paramInt1, int paramInt2, String paramString1, String paramString2);

    List<M> findListOnePageData(Map<String, Object> paramMap, int paramInt1, int paramInt2, String paramString1, String paramString2);
}
