
class Zaini_NurDilah_Player extends Player {
    int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
        float oc1 = 0;
        float oc2 = 0;
        float oc1Percentage = 0;
        float oc2Percentage = 0;
        // if it is the first round, cooperate
        if (n == 0)  {
            return 0;
            // After the 90th round, the player will defect regardless of the opponents actions
        } else if (n > 90) {
            return 1;
        } else {
            for(int i = 0; i < oppHistory1.length;i++){
                if (oppHistory1[i] == 0) {
                    oc1 = oc1 + 1;
                }

                if (oppHistory2[i] == 0) {
                    oc2 = oc2 + 1;
                }
            }
            // Calculate the percentage of cooperation by getting the total number of cooperations the
            // opponent did over the moves the opponent has already done in the history * 100
            oc1Percentage = (oc1 / oppHistory1.length) * 100;
            oc2Percentage = (oc2 / oppHistory2.length) * 100;
            // If the opponents has been cooperating, the player will cooperate
            if(oc1Percentage >= 95){
                if(oc2Percentage >= 95) {
                    return 0;
                }
            }
        }
        // By default, it will defect
        return 1;
    }
}
