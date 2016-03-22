package sk.stefan.utils;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sk.stefan.enums.PublicRoleType;
import sk.stefan.enums.PublicUsefulness;
import sk.stefan.enums.Stability;
import sk.stefan.enums.UserType;
import sk.stefan.enums.VoteAction;
import sk.stefan.enums.VoteResult;
//import sk.stefan.interfaces.Transformable;

/**
 * <p>
 * This will be not static methods - but decoration bean for UniRepo.
 * </p>
 * Created by sveres on 1/31/16.
 */
public class Transformers { //implements Transformable {

//    @Override
    public static byte[] getBytesFromString(String str) {

        return (str == null) ? null : str.getBytes();
//		byte[] b = string.getBytes(Charset.forName("UTF-8"));
//		byte[] b = string.getBytes(StandardCharsets.UTF_8); // Java 7+ only

    }

//    @Override
    public static byte[] getBytesFromInteger(Integer intg) {
        return (intg == null) ? null : intg.toString().getBytes();
    }

//    @Override
    public static byte[] getBytesFromBoolean(Boolean bool) {
        return (bool == null) ? null : bool.toString().getBytes();
    }

//    @Override
    public static byte[] getBytesFromDate(Date date) {
        if (date == null){return null;}

        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return format.format(date).getBytes();
    }

//    @Override
    public static byte[] getbytesFromBytes(Byte[] pole) {
        if (pole == null){return null;}
        
        byte[] b = new byte[pole.length];
        for (int i = 0; i < pole.length; i++) {
            b[i] = pole[i];
        }
        return b;
    }

//    @Override
    public static byte[] getBytesFromPublicRoleType(PublicRoleType prt) {
        return (prt == null) ? null : prt.toString().getBytes();
    }

//    @Override
    public static byte[] getBytesFromVoteAction(VoteAction act) {
        if (act == null){return null;}
        return act.toString().getBytes();
    }

//    @Override
    public static byte[] getBytesFromStability(Stability sta) {
        if (sta == null){return null;}
        return sta.toString().getBytes();
    }

//    @Override
    public static byte[] getBytesFromPublicUsefulness(PublicUsefulness pus) {
        return (pus == null) ? null : pus.toString().getBytes();
    }

//    @Override
    public static byte[] getBytesFromUserType(UserType ut) {
        return (ut == null) ? null : ut.toString().getBytes();
    }

//    @Override
    public static byte[] getBytesFromVoteResult(VoteResult vr) {
        return (vr == null) ? null : vr.toString().getBytes();
    }

//    *********************************************
//    @Override
    public static String getStringFromBytes(byte[] bytes) {
        return new String(bytes);
    }

//    @Override
    public static Integer getIntFromBytes(byte[] bytes) {
        String strInt = new String(bytes);

        return Integer.getInteger(strInt);
    }

//    @Override
    public static Date getDateFromBytes(byte[] bytes) {
        String dateStr = new String(bytes);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date;
        try {
            date = format.parse(dateStr);
        } catch (ParseException ex) {
            return null;
        }

        return date;
    }

//    @Override
    public static Boolean getBooleanFromBytes(byte[] bytes) {
        String boolStr = new String(bytes);

        return Boolean.valueOf(boolStr);
    }

//    @Override
    public static PublicRoleType getPublicRoleTypeFromBytes(byte[] bytes) {

        String enumStr = new String(bytes);

        return PublicRoleType.valueOf(enumStr);

    }

//    @Override
    public static VoteAction getVoteActionFromBytes(byte[] bytes) {
        String enumStr = new String(bytes);

        return VoteAction.valueOf(enumStr);
    }

//    @Override
    public static Stability getStabilityFromBytes(byte[] bytes) {
        String enumStr = new String(bytes);

        return Stability.valueOf(enumStr);

    }

//    @Override
    public static PublicUsefulness getPublicUsefulnessFromBytes(byte[] bytes) {
        String enumStr = new String(bytes);
        return PublicUsefulness.valueOf(enumStr);
    }

//    @Override
    public static UserType getUserTypeFromBytes(byte[] bytes) {
        String enumStr = new String(bytes);
        return UserType.valueOf(enumStr);
    }

//    @Override
    public static VoteResult getVoteResultFromBytes(byte[] bytes) {
        String enumStr = new String(bytes);
        return VoteResult.valueOf(enumStr);
    }

}
