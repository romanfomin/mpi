import config from "config";
import { handleResponse, addAuthHeader } from "@/_helpers";
const rolesUrl = `${config.apiUrl}/api/roles`;

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