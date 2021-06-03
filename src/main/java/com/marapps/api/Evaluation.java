package com.marapps.api;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Map;

public class Evaluation {
    String ErtekeloTanarNeve;
    Map<String, String> ErtekFajta;
    String Jelleg;
    public String KeszitesDatuma;
    @JsonIgnore
    public LocalDateTime KeszitesDatumaParsed = null;
    String LattamozasDatuma;
    Map<String, String> Mod;
    String RogzitesDatuma;
    int SulySzazalekErteke;
    int SzamErtek;
    String SzovegesErtek;
    String SzovegesErtekelesRovidNev;
    Map<String, Object> Tantargy;
    String Tema;
    Map<String, String> Tipus;
    Map<String, String> OsztalyCsoport;
    int SortIndex;
    String Uid;

    public LocalDateTime getKeszitesDatumaParsed() {
        return KeszitesDatumaParsed;
    }

    public void setKeszitesDatumaParsed(LocalDateTime keszitesDatumaParsed) {
        KeszitesDatumaParsed = keszitesDatumaParsed;
    }

    public String getErtekeloTanarNeve() {
        return ErtekeloTanarNeve;
    }

    public void setErtekeloTanarNeve(String ErtekeloTanarNeve) {
        ErtekeloTanarNeve = ErtekeloTanarNeve;
    }

    public Map<String, String> getErtekFajta() {
        return ErtekFajta;
    }

    public void setErtekFajta(Map<String, String> ertekFajta) {
        ErtekFajta = ertekFajta;
    }

    public String getJelleg() {
        return Jelleg;
    }

    public void setJelleg(String jelleg) {
        Jelleg = jelleg;
    }

    public String getKeszitesDatuma() {
        return KeszitesDatuma;
    }

    public void setKeszitesDatuma(String keszitesDatuma) {
        KeszitesDatuma = keszitesDatuma;
    }

    public String getLattamozasDatuma() {
        return LattamozasDatuma;
    }

    public void setLattamozasDatuma(String lattamozasDatuma) {
        LattamozasDatuma = lattamozasDatuma;
    }

    public Map<String, String> getMod() {
        return Mod;
    }

    public void setMod(Map<String, String> mod) {
        Mod = mod;
    }

    public String getRogzitesDatuma() {
        return RogzitesDatuma;
    }

    public void setRogzitesDatuma(String rogzitesDatuma) {
        RogzitesDatuma = rogzitesDatuma;
    }

    public int getSulySzazalekErteke() {
        return SulySzazalekErteke;
    }

    public void setSulySzazalekErteke(int sulySzazalekErteke) {
        SulySzazalekErteke = sulySzazalekErteke;
    }

    public int getSzamErtek() {
        return SzamErtek;
    }

    public void setSzamErtek(int szamErtek) {
        SzamErtek = szamErtek;
    }

    public String getSzovegesErtek() {
        return SzovegesErtek;
    }

    public void setSzovegesErtek(String szovegesErtek) {
        SzovegesErtek = szovegesErtek;
    }

    public String getSzovegesErtekelesRovidNev() {
        return SzovegesErtekelesRovidNev;
    }

    public void setSzovegesErtekelesRovidNev(String szovegesErtekelesRovidNev) {
        SzovegesErtekelesRovidNev = szovegesErtekelesRovidNev;
    }

    public Map<String, Object> getTantargy() {
        return Tantargy;
    }

    public void setTantargy(Map<String, Object> tantargy) {
        Tantargy = tantargy;
    }

    public String getTema() {
        return Tema;
    }

    public void setTema(String tema) {
        Tema = tema;
    }

    public Map<String, String> getTipus() {
        return Tipus;
    }

    public void setTipus(Map<String, String> tipus) {
        Tipus = tipus;
    }

    public Map<String, String> getOsztalyCsoport() {
        return OsztalyCsoport;
    }

    public void setOsztalyCsoport(Map<String, String> osztalyCsoport) {
        OsztalyCsoport = osztalyCsoport;
    }

    public int getSortIndex() {
        return SortIndex;
    }

    public void setSortIndex(int sortIndex) {
        SortIndex = sortIndex;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
