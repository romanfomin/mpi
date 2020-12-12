
import { handleResponse, addAuthHeader } from "../_helpers";

const rolesUrl = `${process.env.REACT_APP_API_URL}/api/roles`;


export const roleService = {
  getAll,
};

function getAll() {
  const requestOptions = {
    method: "GET",
    headers: { "Content-Type": "application/json" }
  };
  return fetch(rolesUrl, addAuthHeader(requestOptions)).then(handleResponse);
}