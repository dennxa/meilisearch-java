package com.meilisearch.sdk;

import static java.util.Collections.singletonList;

import com.meilisearch.sdk.exceptions.MeilisearchException;
import com.meilisearch.sdk.model.Task;
import java.util.List;

/** Wrapper around HttpClient class to use for Meilisearch documents */
class Documents {
    private final HttpClient httpClient;

    protected Documents(Config config) {
        httpClient = config.httpClient;
    }

    /**
     * Retrieves the document at the specified index uid with the specified identifier
     *
     * @param uid Partial index identifier for the requested documents
     * @param identifier ID of the document
     * @return String containing the requested document
     * @throws MeilisearchException if client request causes an error
     */
    String getDocument(String uid, String identifier) throws MeilisearchException {
        String urlPath = "/indexes/" + uid + "/documents/" + identifier;
        return httpClient.get(urlPath, String.class);
    }

    /**
     * Retrieves the document at the specified index
     *
     * @param uid Partial index identifier for the requested documents
     * @return String containing the requested document
     * @throws MeilisearchException if the client request causes an error
     */
    String getDocuments(String uid) throws MeilisearchException {
        String urlPath = "/indexes/" + uid + "/documents";
        return httpClient.get(urlPath, String.class);
    }

    /**
     * Retrieves the document at the specified index
     *
     * @param uid Partial index identifier for the requested documents
     * @param limit Limit on the requested documents to be returned
     * @return String containing the requested document
     * @throws MeilisearchException if the client request causes an error
     */
    String getDocuments(String uid, int limit) throws MeilisearchException {
        String urlQuery = "/indexes/" + uid + "/documents?limit=" + limit;
        return httpClient.get(urlQuery, String.class);
    }

    /**
     * Retrieves the document at the specified index
     *
     * @param uid Partial index identifier for the requested documents
     * @param limit Limit on the requested documents to be returned
     * @param offset Specify the offset of the first hit to return
     * @return String containing the requested document
     * @throws MeilisearchException if the client request causes an error
     */
    String getDocuments(String uid, int limit, int offset) throws MeilisearchException {
        String urlQuery = "/indexes/" + uid + "/documents?limit=" + limit + "&offset=" + offset;
        return httpClient.get(urlQuery, String.class);
    }

    /**
     * Retrieves the document at the specified index
     *
     * @param uid Partial index identifier for the requested documents
     * @param limit Limit on the requested documents to be returned
     * @param offset Specify the offset of the first hit to return
     * @param attributesToRetrieve Document attributes to show
     * @return String containing the requested document
     * @throws MeilisearchException if the client request causes an error
     */
    String getDocuments(String uid, int limit, int offset, List<String> attributesToRetrieve)
            throws MeilisearchException {
        if (attributesToRetrieve == null || attributesToRetrieve.size() == 0) {
            attributesToRetrieve = singletonList("*");
        }

        String attributesToRetrieveCommaSeparated = String.join(",", attributesToRetrieve);
        String urlQuery =
                "/indexes/"
                        + uid
                        + "/documents?limit="
                        + limit
                        + "&offset="
                        + offset
                        + "&attributesToRetrieve="
                        + attributesToRetrieveCommaSeparated;

        return httpClient.get(urlQuery, String.class);
    }

    /**
     * Adds/Replaces a document at the specified index uid
     *
     * @param uid Partial index identifier for the document
     * @param document String containing the document to add
     * @param primaryKey PrimaryKey of the document
     * @return Meilisearch's Task API response
     * @throws MeilisearchException if the client request causes an error
     */
    Task addDocuments(String uid, String document, String primaryKey) throws MeilisearchException {
        String urlQuery = "/indexes/" + uid + "/documents";
        if (primaryKey != null) {
            urlQuery += "?primaryKey=" + primaryKey;
        }
        return httpClient.post(urlQuery, document, Task.class);
    }

    /**
     * Replaces a document at the specified index uid
     *
     * @param uid Partial index identifier for the document
     * @param document String containing the document to replace the existing document
     * @param primaryKey PrimaryKey of the document
     * @return Meilisearch's Task API response
     * @throws MeilisearchException if the client request causes an error
     */
    Task updateDocuments(String uid, String document, String primaryKey)
            throws MeilisearchException {
        String urlPath = "/indexes/" + uid + "/documents";
        if (primaryKey != null) {
            urlPath += "?primaryKey=" + primaryKey;
        }
        return httpClient.put(urlPath, document, Task.class);
    }

    /**
     * Deletes the document at the specified index uid with the specified identifier
     *
     * @param uid Partial index identifier for the requested document
     * @param identifier ID of the document
     * @return Meilisearch's Task API response
     * @throws MeilisearchException if the client request causes an error
     */
    Task deleteDocument(String uid, String identifier) throws MeilisearchException {
        String urlPath = "/indexes/" + uid + "/documents/" + identifier;
        return httpClient.delete(urlPath, Task.class);
    }

    /**
     * Deletes the documents at the specified index uid with the specified identifiers
     *
     * @param uid Partial index identifier for the requested documents
     * @param identifiers ID of documents to delete
     * @return Meilisearch's Task API response
     * @throws MeilisearchException if the client request causes an error
     */
    Task deleteDocuments(String uid, List<String> identifiers) throws MeilisearchException {
        String urlPath = "/indexes/" + uid + "/documents/" + "delete-batch";
        return httpClient.post(urlPath, identifiers, Task.class);
    }

    /**
     * Deletes all documents at the specified index uid
     *
     * @param uid Partial index identifier for the requested documents
     * @return Meilisearch's Task API response
     * @throws MeilisearchException if the client request causes an error
     */
    Task deleteAllDocuments(String uid) throws MeilisearchException {
        String urlPath = "/indexes/" + uid + "/documents";
        return httpClient.delete(urlPath, Task.class);
    }
}
