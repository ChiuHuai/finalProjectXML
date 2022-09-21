package com.example.littleProject.model.entity.xml;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "Symbols")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class Symbols {
    @XmlElement(name = "Symbol")
    private List<Symbol> symbolList;

    @Override
    public String toString() {
        return symbolList.stream().map(e -> e.toString())
                .collect(Collectors.joining("\n"));
    }

}
