
import { handleResponse, addAuthHeader } from "../_helpers";
const applicationsUrl = `${process.env.REACT_APP_API_URL}/api/applications`;

export const applicationService = {
  // getFile,
  getAll,
  getAllTypes,
  getAllStates,
  create,
  update,
  updateStates
};

// function getFile(id, callback) {
//   const requestOptions = {
//     method: "GET",
//     headers: { "Content-Type": "application/octet-stream" },
//   };
//   return fetch("/api/files/" + id, addAuthHeader(requestOptions))
//     .then((response) => response.blob())
//     .then((blobby) => {
//       let objectUrl = window.URL.createObjectURL(blobby);

//       anchor.href = objectUrl;
//       anchor.download = "some-file.pdf";
//       anchor.click();

//       window.URL.revokeObjectURL(objectUrl);
//     });
// }

function getAll(type) {
  var url = new URL(applicationsUrl),
    params = { type };
  Object.keys(params).forEach((key) =>
    url.searchParams.append(key, params[key])
  );
  const requestOptions = {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  };
  return fetch(url, addAuthHeader(requestOptions)).then(handleResponse);
}

function getAllTypes() {
  const requestOptions = {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  };
  return fetch(applicationsUrl + "/types", addAuthHeader(requestOptions)).then(
    handleResponse
  );
}

function getAllStates() {
  const requestOptions = {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  };
  return fetch(applicationsUrl + "/states", addAuthHeader(requestOptions)).then(
    handleResponse
  );
}

function updateStates(id, state) {
  const formData = new FormData();
  formData.append("state", state);
  const requestOptions = {
    method: "PUT",
    // headers: { "Content-Type": "application/json" },
    body: formData
  };
  return fetch(applicationsUrl + `/${id}/state`, addAuthHeader(requestOptions)).then(
    handleResponse
  );
}

function create(appDate, comment, price, file, app_type) {
  const formData = new FormData();
  formData.append("name", comment);
  formData.append("price", price);
  formData.append("appDate", appDate);
  formData.append("app_type", app_type);
  formData.append("attachments", file);

  const requestOptions = {
    method: "POST",
    body: formData,
  };
  return fetch(applicationsUrl, addAuthHeader(requestOptions)).then(
    handleResponse
  );
}

function update(id, airDate, text) {
  const requestOptions = {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ id, airDate, text }),
  };
  return fetch(applicationsUrl + "/" + id, addAuthHeader(requestOptions)).then(
    handleResponse
  );
}
