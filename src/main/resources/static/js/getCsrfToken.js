/**
 * getCsrfToken
 */

function getCsrfToken() {
    return fetch('/csrf-token-endpoint')
        .then(response => response.json())
        .then(data => data.csrfToken)
        .catch(error => console.error('Failed to fetch CSRF token:', error));
}
