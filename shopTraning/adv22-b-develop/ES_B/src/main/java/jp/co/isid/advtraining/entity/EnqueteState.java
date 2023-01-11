package jp.co.isid.advtraining.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

/**
 *
 */
@Entity
@Table(name = "enquete_state")
public class EnqueteState {

    /**  */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enquete_state_id")
    Integer enqueteStateId;

    /**  */
    @Column(name = "enquete_state")
    String enqueteState;

    /**
     * Returns the enqueteStateId.
     *
     * @return the enqueteStateId
     */
    public Integer getEnqueteStateId() {
        return enqueteStateId;
    }

    /**
     * Sets the enqueteStateId.
     *
     * @param enqueteStateId the enqueteStateId
     */
    public void setEnqueteStateId(Integer enqueteStateId) {
        this.enqueteStateId = enqueteStateId;
    }

    /**
     * Returns the enqueteState.
     *
     * @return the enqueteState
     */
    public String getEnqueteState() {
        return enqueteState;
    }

    /**
     * Sets the enqueteState.
     *
     * @param enqueteState the enqueteState
     */
    public void setEnqueteState(String enqueteState) {
        this.enqueteState = enqueteState;
    }
}
