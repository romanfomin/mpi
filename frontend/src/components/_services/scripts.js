
import { handleResponse, addAuthHeader } from "../_helpers";
const scriptsUrl = `${process.env.REACT_APP_API_URL}/api/scripts`;

export const scriptService = {
  getAll,
  create, 
  update,
};

function getAll() {
  const requestOptions = {
    method: "GET",
    headers: { "Content-Type": "application/json" }
  };
  return fetch(scriptsUrl, addAuthHeader(requestOptions)).then(handleResponse);
}



function create(airDate, text) {
  const requestOptions = {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ airDate, text })
  };
  return fetch(scriptsUrl, addAuthHeader(requestOptions)).then(handleResponse);
}

function update(id, airDate, text){
  const requestOptions = {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ id, airDate, text })
  };
  return fetch(scriptsUrl + '/' + id, addAuthHeader(requestOptions)).then(handleResponse);
}