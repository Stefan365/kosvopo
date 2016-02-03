package sk.stefan.interfaces;

import java.util.Date;

import sk.stefan.enums.PublicRoleType;
import sk.stefan.enums.PublicUsefulness;
import sk.stefan.enums.Stability;
import sk.stefan.enums.VoteAction;

/**
 * Znackovaci interface.
 * <p>
 * Created by sveres on 1/31/16.
 */
public interface Transformable {

	public byte[] getBytesFromString(String str);

	public byte[] getBytesFromInteger(Integer intg);

	public byte[] getBytesFromBoolean(Boolean str);

	public byte[] getBytesFromDate(Date date);

	public byte[] getBytesFromPublicRoleType(PublicRoleType prt);

	public byte[] getBytesFromVoteAction(VoteAction va);

	public byte[] getBytesFromStability(Stability sta);
        
	public byte[] getBytesFromPublicUsefulness(PublicUsefulness pus);

        //    Stability,
//    PublicUsefulness,

//****************************************************************        
	public String getStringFromBytes(byte[] bytes);

	public Integer getIntFromBytes(byte[] bytes);

	public Date getDateFromBytes(byte[] bytes);

	public Boolean getBooleanFromBytes(byte[] bytes);

	public byte[] getbytesFromBytes(Byte[] bytes);

	public PublicRoleType getPublicRoleTypeFromBytes(byte[] bytes);

	public VoteAction getVoteActionFromBytes(byte[] bytes);
        
        public Stability getStabilityFromBytes(byte[] bytes);
        
        public PublicUsefulness getPublicUsefulnessFromBytes(byte[] bytes);
        
        
}
