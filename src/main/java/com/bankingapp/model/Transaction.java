/*
 *
 *  Copyright (c) 2018-2020 Givantha Kalansuriya, This source is a part of
 *   Staxrt - sample application source code.
 *   http://staxrt.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.bankingapp.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Transaction extends BaseObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "account_from_id")
    private Account accountFrom;

    @ManyToOne
    @JoinColumn(name = "account_to_id")
    private Account accountTo;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "transaction_timestamp", nullable = false)
    private Date transactionTimestamp;

    @Column(name = "amount_in_cents", nullable = false)
    private Long amountInCents;

    public Transaction(Account accountFrom, Account accountTo, Long amountInCents){
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amountInCents = amountInCents;
    };

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", transactionTimestamp=" + transactionTimestamp +
                ", amountInCents=" + amountInCents +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Transaction that = (Transaction) o;

        if (id != that.id) return false;
        if (accountFrom != null ? !accountFrom.equals(that.accountFrom) : that.accountFrom != null) return false;
        if (accountTo != null ? !accountTo.equals(that.accountTo) : that.accountTo != null) return false;
        if (transactionTimestamp != null ? !transactionTimestamp.equals(that.transactionTimestamp) : that.transactionTimestamp != null)
            return false;
        return amountInCents != null ? amountInCents.equals(that.amountInCents) : that.amountInCents == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (accountFrom != null ? accountFrom.hashCode() : 0);
        result = 31 * result + (accountTo != null ? accountTo.hashCode() : 0);
        result = 31 * result + (transactionTimestamp != null ? transactionTimestamp.hashCode() : 0);
        result = 31 * result + (amountInCents != null ? amountInCents.hashCode() : 0);
        return result;
    }

}
