package com.Vidstream.vidstream.model;

import java.math.BigDecimal;

public record ApiRequest(
        int sender_id,    // Sender ID
        int receiver_id, // Receiver ID
        BigDecimal amount  // Amount (Decimal(15, 2))
) {}

