package com.zenyte.api.client.query;

import com.zenyte.api.client.APIClient;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @author Corey
 * @since 07/06/19
 */
public class VoteCheckRequest {
    private static final Logger log = LoggerFactory.getLogger(VoteCheckRequest.class);
    private final String username;

    public VoteCheckRequest(final String username) {
        this.username = username;
    }

    public int execute() throws RuntimeException {
        final OkHttpClient http = APIClient.CLIENT;
        final HttpUrl url = APIClient.urlBuilder().addPathSegment("account").addPathSegment("vote").addPathSegment(username.replaceAll("_", " ")).build();
        final Request request = new Request.Builder().url(url).get().build();
        try {
            try (Response response = http.newCall(request).execute()) {
                final ResponseBody responseBody = response.body();
                if (responseBody == null) {
                    throw new RuntimeException("Response body is not present.");
                }
                if (!response.isSuccessful()) {
                    throw new RuntimeException("Response is not successful.");
                }
                final String body = responseBody.string();
                //If empty besides {}
                if (body.length() == 0) {
                    throw new RuntimeException("Response body is empty.");
                }
                return Integer.parseInt(body);
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean equals(@Nullable final Object o) {
        if (o == this) return true;
        if (!(o instanceof VoteCheckRequest other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        return Objects.equals(this$username, other$username);
    }

    protected boolean canEqual(@Nullable final Object other) {
        return other instanceof VoteCheckRequest;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        return result;
    }

    @NotNull
    @Override
    public String toString() {
        return "VoteCheckRequest(username=" + this.getUsername() + ")";
    }
}
