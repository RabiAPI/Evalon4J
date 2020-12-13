package com.evalon4j.frameworks.spring
/**
 * A subclass of {@link MimeType} that adds support for quality parameters
 * as defined in the HTTP specification.
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @author Rossen Stoyanchev
 * @author Sebastien Deleuze
 * @author Kazuki Shimizu
 * @author Sam Brannen
 * @since 3.0* @see <ahref="https://tools.ietf.org/html/rfc7231#section-3.1.1.1" >
 *     HTTP 1.1: Semantics and Content, section 3.1.1.1</a>
 */
class MediaType {
    /**
     * A String equivalent of {@link MediaType#ALL}.
     */
    public static final String ALL_VALUE = "*/*";


    /**
     * A String equivalent of {@link MediaType#APPLICATION_ATOM_XML}.
     */
    public static final String APPLICATION_ATOM_XML_VALUE = "application/atom+xml";


    /**
     * A String equivalent of {@link MediaType#APPLICATION_CBOR}.
     * @since 5.2
     */
    public static final String APPLICATION_CBOR_VALUE = "application/cbor";


    /**
     * A String equivalent of {@link MediaType#APPLICATION_FORM_URLENCODED}.
     */
    public static final String APPLICATION_FORM_URLENCODED_VALUE = "application/x-www-form-urlencoded";


    /**
     * A String equivalent of {@link MediaType#APPLICATION_JSON}.
     * @see #APPLICATION_JSON_UTF8_VALUE
     */
    public static final String APPLICATION_JSON_VALUE = "application/json";


    /**
     * A String equivalent of {@link MediaType#APPLICATION_JSON_UTF8}.
     * @deprecated as of 5.2 in favor of {@link #APPLICATION_JSON_VALUE}
     * since major browsers like Chrome
     * <a href="https://bugs.chromium.org/p/chromium/issues/detail?id=438464">
     * now comply with the specification</a> and interpret correctly UTF-8 special
     * characters without requiring a {@code charset=UTF-8} parameter.
     */
    @Deprecated
    public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";


    /**
     * A String equivalent of {@link MediaType#APPLICATION_OCTET_STREAM}.
     */
    public static final String APPLICATION_OCTET_STREAM_VALUE = "application/octet-stream";


    /**
     * A String equivalent of {@link MediaType#APPLICATION_PDF}.
     * @since 4.3
     */
    public static final String APPLICATION_PDF_VALUE = "application/pdf";


    /**
     * A String equivalent of {@link MediaType#APPLICATION_PROBLEM_JSON}.
     * @since 5.0
     */
    public static final String APPLICATION_PROBLEM_JSON_VALUE = "application/problem+json";


    /**
     * A String equivalent of {@link MediaType#APPLICATION_PROBLEM_JSON_UTF8}.
     * @since 5.0* @deprecated as of 5.2 in favor of {@link #APPLICATION_PROBLEM_JSON_VALUE}
     * since major browsers like Chrome
     * <a href="https://bugs.chromium.org/p/chromium/issues/detail?id=438464">
     * now comply with the specification</a> and interpret correctly UTF-8 special
     * characters without requiring a {@code charset=UTF-8} parameter.
     */
    @Deprecated
    public static final String APPLICATION_PROBLEM_JSON_UTF8_VALUE = "application/problem+json;charset=UTF-8";


    /**
     * A String equivalent of {@link MediaType#APPLICATION_PROBLEM_XML}.
     * @since 5.0
     */
    public static final String APPLICATION_PROBLEM_XML_VALUE = "application/problem+xml";


    /**
     * A String equivalent of {@link MediaType#APPLICATION_RSS_XML}.
     * @since 4.3.6
     */
    public static final String APPLICATION_RSS_XML_VALUE = "application/rss+xml";


    /**
     * A String equivalent of {@link MediaType#APPLICATION_STREAM_JSON}.
     * @since 5.0
     */
    public static final String APPLICATION_STREAM_JSON_VALUE = "application/stream+json";


    /**
     * A String equivalent of {@link MediaType#APPLICATION_XHTML_XML}.
     */
    public static final String APPLICATION_XHTML_XML_VALUE = "application/xhtml+xml";


    /**
     * A String equivalent of {@link MediaType#APPLICATION_XML}.
     */
    public static final String APPLICATION_XML_VALUE = "application/xml";


    /**
     * A String equivalent of {@link MediaType#IMAGE_GIF}.
     */
    public static final String IMAGE_GIF_VALUE = "image/gif";


    /**
     * A String equivalent of {@link MediaType#IMAGE_JPEG}.
     */
    public static final String IMAGE_JPEG_VALUE = "image/jpeg";

    /**
     * A String equivalent of {@link MediaType#IMAGE_PNG}.
     */
    public static final String IMAGE_PNG_VALUE = "image/png";

    /**
     * A String equivalent of {@link MediaType#MULTIPART_FORM_DATA}.
     */
    public static final String MULTIPART_FORM_DATA_VALUE = "multipart/form-data";

    /**
     * A String equivalent of {@link MediaType#MULTIPART_MIXED}.
     * @since 5.2
     */
    public static final String MULTIPART_MIXED_VALUE = "multipart/mixed";

    /**
     * A String equivalent of {@link MediaType#MULTIPART_RELATED}.
     * @since 5.2.5
     */
    public static final String MULTIPART_RELATED_VALUE = "multipart/related";


    /**
     * A String equivalent of {@link MediaType#TEXT_EVENT_STREAM}.
     * @since 4.3.6
     */
    public static final String TEXT_EVENT_STREAM_VALUE = "text/event-stream";


    /**
     * A String equivalent of {@link MediaType#TEXT_HTML}.
     */
    public static final String TEXT_HTML_VALUE = "text/html";


    /**
     * A String equivalent of {@link MediaType#TEXT_MARKDOWN}.
     * @since 4.3
     */
    public static final String TEXT_MARKDOWN_VALUE = "text/markdown";

    /**
     * A String equivalent of {@link MediaType#TEXT_PLAIN}.
     */
    public static final String TEXT_PLAIN_VALUE = "text/plain";


    /**
     * A String equivalent of {@link MediaType#TEXT_XML}.
     */
    public static final String TEXT_XML_VALUE = "text/xml";

    private static final String PARAM_QUALITY_FACTOR = "q";
}
