package com.mohress.training.util;


import org.apache.commons.lang.RandomStringUtils;

import java.util.Date;

/**
 * 序列化生成
 *
 * @author youtao.wan
 * @date 16-12-5
 */
public class SequenceCreator {
    private static Sequencer sequencer = new Sequencer();
    private static char[] RANDOM_NUMS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static String getAgencyId(){
        return getSequenceId(SequenceType.AG);
    }

    private static String getSequenceId(SequenceType sequenceType) {
        return sequenceType.name() + DateUtil.format(new Date(), "yyMMddHHmmss") + sequencer.getSequence() + RandomStringUtils.random(
                2, RANDOM_NUMS);
    }

    public static String getTeacherId() {
        return getSequenceId(SequenceType.TE);
    }

    public static String getClassId() {
        return getSequenceId(SequenceType.CL);
    }

    public static String getStudentId() {
        return getSequenceId(SequenceType.ST);
    }


    static class Sequencer {
        private int sequence;

        public Sequencer() {
            this.sequence = (int) (System.currentTimeMillis() % (1000 * 60));
        }

        public synchronized String getSequence() {
            sequence++;
            if (sequence > 999999) {
                sequence = sequence - 1000000;
            }
            return LocalMachine.getMachine() + String.format("%06d", sequence);
        }
    }

    enum SequenceType {
        AG, TE, CL, ST,
    }
}
