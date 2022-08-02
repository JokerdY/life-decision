package com.life.decision.support.bo;


import lombok.Data;

import java.text.DecimalFormat;
import java.util.List;

@Data
public class TotalCaloriesEntity {
    List<ElementEntity> entityList;

    public static class ElementEntity {
        /**
         * 成分
         */
        private final String element;
        /**
         * 占比
         */
        private final String proportion;
        private final String weight;
        private final String proportionStr;

        public ElementEntity(String element, String proportion, String weight) {
            this.element = element;
            this.proportion = proportion;
            this.weight = weight;
            double pDouble = 0;
            try {
                pDouble = Double.parseDouble(proportion);
            } catch (Exception ignore) {

            }
            this.proportionStr = new DecimalFormat("0.00").format(pDouble / 100.0);
        }

        public String getElement() {
            return element;
        }

        public String getProportion() {
            return proportion;
        }

        public String getWeight() {
            return weight;
        }

        public String getProportionStr() {
            return proportionStr;
        }
    }
}
