const domain="https://master-chef-devs.herokuapp.com";

export default {
    endpoint: {
        login: 'https://master-chef-devs.herokuapp.com/rest-auth/login/',
        register: 'https://master-chef-devs.herokuapp.com/createUser/',
        restaurant: {
            list: `${domain}/api/restaurant/`,
            walls: `${domain}/api/pereti/`,
            tables: `${domain}/api/mese/`,
            reservations: `${domain}/api/rezervari/`,
        }
    }
};
