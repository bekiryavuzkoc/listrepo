package com.android.nesinecasestudy.domain.utils

enum class NetworkError : Error {
    REQUEST_TIMEOUT {
        override var message: String? = null
    },
    UNAUTHORIZED {
        override var message: String? = null
    },
    CONFLICT {
        override var message: String? = null
    },
    NOT_FOUND {
        override var message: String? = null
    },
    NOT_ACCEPTABLE {
        override var message: String? = null
    },
    TOO_MANY_REQUESTS {
        override var message: String? = null
    },
    NO_INTERNET {
        override var message: String? = null
    },
    PAYLOAD_TOO_LARGE {
        override var message: String? = null
    },
    UNPROCESSABLE_ENTITY {
        override var message: String? = null
    },
    SERVER_ERROR {
        override var message: String? = null
    },
    SERIALIZATION {
        override var message: String? = null
    },
    BAD_REQUEST {
        override var message: String? = null
    },
    EMPTY {
        override var message: String? = null
    },
    UNKNOWN {
        override var message: String? = null
    },
    SUCCESS_OTHER_THAN_200 {
        override var message: String? = null
    };
}