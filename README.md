## Movies in San Francisco service

The **endpoints** available in this service are 2:

1. *movie/from-file* that downloads the movies played in San Francisco from a CSV file and shows just the title and the
 location.
2. *movie/from-api* that downloads the movies played in San Francisco from an open API through a spring boot rest client
 and shows just the title and the location.
 
**NOTE:** both the endpoints are able to filter the movies by their title, to apply the filter is necessary to add the 
request parameter title (i.e. movie/from-file?title=myTitle)