package cn.springcloud.book.gateway.model;


import com.alibaba.fastjson.JSON;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URI;
import java.util.List;

@Entity
@Table(name = "gateway_define")
public class GatewayDefine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String uri;

    private String predicates;

    private String filters;
    /**
     * order 为mysql关键字，不可使用
     */
    @Column(name = "route_order")
    private int order;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }
    public void setUri(URI uri) {
        this.uri = uri.toString();
    }
    public String getPredicates() {
        return this.predicates;
    }

    public void setPredicates(String predicates) {
        this.predicates = predicates;
    }

    public List<PredicateDefinition> getPredicateDefinition() {
        if (this.predicates != null) {
            List<PredicateDefinition> predicateDefinitionList = JSON.parseArray(this.predicates, PredicateDefinition.class);
            return predicateDefinitionList;
        } else {
            return null;
        }
    }

    /*public String getFilters() {
        return filters;
    }*/

    public List<FilterDefinition> getFilterDefinition() {
        if (this.filters != null) {
            List<FilterDefinition> filterDefinitionList = JSON.parseArray(this.filters, FilterDefinition.class);
            return filterDefinitionList;
        } else {
            return null;
        }
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "GatewayDefine{" +
                "id='" + id + '\'' +
                ", uri='" + uri + '\'' +
                ", predicates='" + predicates + '\'' +
                ", filters='" + filters + '\'' +
                '}';
    }
}
