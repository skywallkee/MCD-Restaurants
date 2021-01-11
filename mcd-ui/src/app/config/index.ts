const domain="https://master-chef-devs.herokuapp.com";

export default {
    endpoint: {
        login: `${domain}/rest-auth/login/`,
        register: `${domain}/createUser/`,
        restaurant: {
            list: `${domain}/api/restaurant/`,
            walls: `${domain}/api/pereti/`,
            tables: `${domain}/api/mese/`,
            reservations: `${domain}/api/rezervari/`,
            reviews: `${domain}/api/review/`,
            statistics: {
                byDay: `${domain}/statisticsByDay/`,
                byDayByHour:`${domain}/statisticsByDayByHour/`
            }
        }
    }
};
