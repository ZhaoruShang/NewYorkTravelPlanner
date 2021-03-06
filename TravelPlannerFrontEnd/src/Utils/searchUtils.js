export const searchByKeyword = (keyword) => {
    return fetch(`/search/${keyword}`).then((response) => {
      if (response.status !== 200) {
        throw Error("Fail to get search results");
      }   
      return response.json();
    });
  };

  export const searchByRange = (lat, lng, range) => {
    return fetch(`/search/${lat}/${lng}/${range}`).then((response) => {
      if (response.status !== 200) {
        throw Error("Fail to get search results");
      }   
      return response.json();
    });
  };

  export const searchNearbyPlaces = (route, placeType) => {
    return fetch(`/search/route/${placeType}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(route),
      credentials: "include",
    }).then((response) => {
      if (response.status !== 200) {
        throw Error("Fail to get places");
      }
      return response.json();
    });
  };

