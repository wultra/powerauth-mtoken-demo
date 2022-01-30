# Demo of Wultra Mobile Token

[![GitHub issues](https://img.shields.io/github/issues/wultra/powerauth-mtoken-demo.svg?maxAge=2592000)](https://github.com/wultra/powerauth-mtoken-demo/issues)
[![Twitter](https://img.shields.io/badge/twitter-@wultra-blue.svg?style=flat)](http://twitter.com/wultra)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](./LICENSE)

A demo web application that authenticates users via Wultra Mobile Token.

## Installation

1. Install JDK 11
2. Install Maven 3.6
3. Install PostgreSQL 13.5
4. Create an API Key at https://sendgrid.com/
5. Get credentials to the Wultra Mobile Token API
6. Clone this repository
7. Run `mvn clean package spring-boot:repackage`
8. Configure the following application properties:
   * `email.email-verification.body` to the body of the sent email verification emails in the format of a MessageFormat pattern with a single format element representing a UUID verification code (e.g. `Your email verification link is: http://example.com?code={0}.`)
   * `email.from.address` to the email address of the sent emails' sender
   * `sendgrid.api-key` to the SendGrid API Key
   * `spring.datasource.url` to the URL of the PostgreSQL database
   * `spring.datasource.username` to the name of the PostgreSQL user
   * `spring.datasource.password` to the password of the PostgreSQL user
   * `wultra-mtoken.rest.root-uri` to the base URL of the Wultra Mobile Token API
   * `wultra-mtoken.rest.username` to the name of the Wultra Mobile Token API user
   * `wultra-mtoken.rest.password` to the password of the Wultra Mobile Token API user
   * optionally, `accesstoken.bytes` to the length of access tokens in bytes before encoding to Base64
   * optionally, `email.email-verification.subject` to the subject of the sent email verification emails
   * optionally, `email.from.name` to the display name of the sent emails' sender
9. Run `java -jar target/mtoken-0.0.1-SNAPSHOT.jar`

## Documentation

The documentation is available at the [Wultra Developer Portal](https://developers.wultra.com/products/mobile-token).

# License

All sources are licensed using the Apache 2.0 license. You can use them with no restrictions.

# Contact

If you need any assistance, do not hesitate to drop us a line at [hello@wultra.com](mailto:hello@wultra.com) or our official [gitter.im/wultra](https://gitter.im/wultra) channel.
