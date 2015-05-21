package sk.stefan.listeners;

/**
 * Logika tohoto listeneru je nasledovna: K formularu logicky patria aj tlacitka
 * OK a CANCEL, tj. nieje je dobre, aby formular bol jeden celok a tlacitka
 * OK/CANCEL druhy a potom sa to dodatocne prepajalo, (formular by mal byt
 * vlozitelny kdekolvek, nielen do okna) Kedze vsak s OK/CANCEL mozu byt
 * spriahnute aj ine akcie nesuvisiace s logikou formulara (napr. zatvaranie
 * okna v ktorom je formular) je potrebne prave taketo rozhranie.
 * 
* @author stefan
 */
public interface OkCancelListener {

    /**
     * to entId je tam len a vyhradne kvoli tomu, ze z InputVoteLayout treba preniest 
     * vote id, ktore vzniklo zapisom do DB do pritomniLayout aby sa mohlo predat novovzniknutym 
     * voteOfRoles.
     * @param entId
     */
    public void doOkAction(Integer entId);

    public void doCancelAction();


}
