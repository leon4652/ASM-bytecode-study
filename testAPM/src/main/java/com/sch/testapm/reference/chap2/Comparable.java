package com.sch.testapm.reference.chap2;

public interface Comparable extends Mesurable{
    //Comparable 인터페이스가 Mesurable 인터페이스를 확장한다고 가정
    int LESS = -1;
    int EQUAL = 0;
    int GREATER = 1;
    int compareTo(Object o);
}
