const DEFAULT_WEATHER = {
    type: 'Clear',
    temperature: 27
};
const DEFAULT_ADS=[];

const timer = () => {
  const start = Date.now()
  return () => Date.now() - start
}

const app = new Vue({
    el: '#app',
    data: {
        news: [],
        weather: DEFAULT_WEATHER,
        ads: DEFAULT_ADS,
        response: null
    },
    mounted: function() {
        this.refresh();
    },
    computed: {
        isFallbackWeather: function() {
           return this.weather.type === DEFAULT_WEATHER.type
                    && this.weather.temperature === DEFAULT_WEATHER.temperature;
        },
        isFallbackAds: function() {
           return this.ads.length === 0;
        },
        weatherImage: function() {
            return 'images/' + this.weather.type + '.svg';
        }
    },
    methods: {
        refresh: function() {
            const responseTimer = timer();
            axios.get("/api/news/paris")
                .then(response => {
                    const elapsed = responseTimer();
                    this.response = {
                        status: response.status,
                        time: elapsed
                    };
                    this.news = response.data.articles;
                    this.weather = response.data.weather;
                    this.ads = response.data.ads;
                })
                .catch(error => {
                    const elapsed = responseTimer();
                    this.response = {
                        status: error.status,
                        time: elapsed
                    };
                    this.news = [];
                    this.weather = DEFAULT_WEATHER;
                    this.ads = DEFAULT_ADS;
                });
        }
    }
})