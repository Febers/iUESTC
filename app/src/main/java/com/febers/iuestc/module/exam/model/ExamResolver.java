package com.febers.iuestc.module.exam.model;

import com.febers.iuestc.entity.BeanExam;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

class ExamResolver {

    static List<BeanExam> resolveUnderExamHtml(String html) {
        List<BeanExam> examList = new ArrayList<>();
        Document docFinal = Jsoup.parse(html);
        Elements elsFinal = docFinal.select("tr[align=\"center\"][onclick=\"onRowChange(event)\"]");
        for (int i = 0; i < elsFinal.size(); i++) {
            Element e = elsFinal.get(i);
            BeanExam exam = new BeanExam();
            Elements perExam = e.select("td");
            exam.setNum(perExam.get(0).text());
            exam.setName(perExam.get(1).text());
            if (perExam.size() < 6) {
                exam.setNoPost(true);
                examList.add(exam);
                continue;
            }
            if (perExam.size() == 7) {
                exam.setDate(perExam.get(2).text());
                exam.setTime(perExam.get(3).text());
                exam.setPosition("位置未发布");
                exam.setNoPost(false);
                examList.add(exam);
                continue;
            }
            if (perExam.size() == 8) {
                exam.setDate(perExam.get(2).text());
                exam.setTime(perExam.get(3).text());
                exam.setPosition(perExam.get(4).text());
                exam.setSeat(perExam.get(5).text());
                exam.setStatus(perExam.get(6).text());
                exam.setOther(perExam.get(7).text());
                exam.setNoPost(false);
                examList.add(exam);
            }
        }
        return examList;
    }
}
