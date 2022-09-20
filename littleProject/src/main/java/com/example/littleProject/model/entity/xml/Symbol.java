package com.example.littleProject.model.entity.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Symbol {
    @XmlAttribute(name = "id")
    private String id;
    @XmlAttribute(name = "shortname")
    private String shortname;
    @XmlAttribute(name = "dealprice")
    private String dealprice;
    @XmlAttribute(name = "mtype")
    private String mtype;

    @Override
    public String toString() {
        return id + "," + shortname + "," + dealprice + "," + mtype;
    }
}
