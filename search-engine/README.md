Search Engine
-------------

This is a tiny search engine uses 3 levels of cache
	
 1. in memory
 2. historic seach
 3. lucene search	

Build 
-----

[![Build Status](https://travis-ci.org/nsaravanas/my-space.svg?branch=master)](https://travis-ci.org/nsaravanas/my-space)

Technologies
----

 - Java 8
 - Spring Boot 1.4.0
 - JPA 2.1
 - Hibernate 5.0.9
 - Hibernate Search 5.5.4
 - Lucene 5.3.1
 - HSQL 2.3.3

REST Endpoints
--------------
 1. initialize
 2. getall
 3. save
 4. search
 5. delete
 6. clear
 
 
End point usage
---------------

##1.INITIALIZE

Initlialize will save/index a set of predefined pages

###<i>Request</i>

```sh
GET /initialize
```

###<i>Response</i>

```sh
{
  "initialize": "ok",
  "pages": [
    {
      "name": "P1",
      "weight": null,
      "url": "www.p1.com",
      "stats": {
        "id": 1,
        "lastVisit": null,
        "totalVisit": 0
      },
      "tags": [
        "ford",
        "car",
        "review"
      ],
      "subPages": null
    },
    {
      "name": "P2",
      "weight": null,
      "url": "www.p2.com",
      "stats": {
        "id": 2,
        "lastVisit": null,
        "totalVisit": 0
      },
      "tags": [
        "review",
        "car"
      ],
      "subPages": null
    },
    {
      "name": "P3",
      "weight": null,
      "url": "www.p3.com",
      "stats": {
        "id": 3,
        "lastVisit": null,
        "totalVisit": 0
      },
      "tags": [
        "review",
        "ford"
      ],
      "subPages": null
    },
    {
      "name": "P4",
      "weight": null,
      "url": "www.p4.com",
      "stats": {
        "id": 4,
        "lastVisit": null,
        "totalVisit": 0
      },
      "tags": [
        "toyota",
        "car"
      ],
      "subPages": null
    },
    {
      "name": "P5",
      "weight": null,
      "url": "www.p5.com",
      "stats": {
        "id": 5,
        "lastVisit": null,
        "totalVisit": 0
      },
      "tags": [
        "honda",
        "car"
      ],
      "subPages": null
    },
    {
      "name": "P6",
      "weight": null,
      "url": "www.p6.com",
      "stats": {
        "id": 6,
        "lastVisit": null,
        "totalVisit": 0
      },
      "tags": [
        "car"
      ],
      "subPages": null
    }
  ]
}
```


##2.GET ALL

getall will return all saved pages and additional information associated with each page

###<i>Request</i>

```sh
GET /getall
```

###<i>Response</i>

```sh
[
  {
    "name": "P1",
    "weight": null,
    "url": "www.p1.com",
    "stats": {
      "id": 1,
      "lastVisit": null,
      "totalVisit": 0
    },
    "tags": [
      "ford",
      "car",
      "review"
    ],
    "subPages": []
  },
  {
    "name": "P2",
    "weight": null,
    "url": "www.p2.com",
    "stats": {
      "id": 2,
      "lastVisit": null,
      "totalVisit": 0
    },
    "tags": [
      "review",
      "car"
    ],
    "subPages": []
  },
  {
    "name": "P3",
    "weight": null,
    "url": "www.p3.com",
    "stats": {
      "id": 3,
      "lastVisit": null,
      "totalVisit": 0
    },
    "tags": [
      "review",
      "ford"
    ],
    "subPages": []
  },
  {
    "name": "P4",
    "weight": null,
    "url": "www.p4.com",
    "stats": {
      "id": 4,
      "lastVisit": null,
      "totalVisit": 0
    },
    "tags": [
      "toyota",
      "car"
    ],
    "subPages": []
  },
  {
    "name": "P5",
    "weight": null,
    "url": "www.p5.com",
    "stats": {
      "id": 5,
      "lastVisit": null,
      "totalVisit": 0
    },
    "tags": [
      "honda",
      "car"
    ],
    "subPages": []
  },
  {
    "name": "P6",
    "weight": null,
    "url": "www.p6.com",
    "stats": {
      "id": 6,
      "lastVisit": null,
      "totalVisit": 0
    },
    "tags": [
      "car"
    ],
    "subPages": []
  }
]
```

##3.SAVE

save to save a page

###<i>Request</i>

```sh
POST /save
Content-Type: application/json
[
    {
    "name": "P7",
    "url": "www.p7.com",
    "tags": [
      "ford",
      "car",
      "review"
    ],
    "subPages": []
  }
]
```

###<i>Response</i>

```sh
{
  "saved_pages": [
    "P7"
  ]
}
```

##4.SEARCH

search is to search a page with tags, there are two variations in search, by default it will return from in-memory/historic search/lucene cache, this can be overridden by passing cache and index as false

###<i>Request</i>

Cache

```sh
POST /search
Content-Type: application/json
{
    "search" : {
        "tags":["car"]
    }
}
```

No-cache

```sh
POST /search
Content-Type: application/json
{
    "search" : {
        "tags":["car"],
        "cache":false,
        "index":false
    }
}
```

###<i>Response</i>

```sh
{
  "timeTaken_in_mills": 289,
  "pages": [
    {
      "name": "P6",
      "weight": 64,
      "url": "www.p6.com",
      "stats": {
        "id": 6,
        "lastVisit": null,
        "totalVisit": 0
      },
      "tags": [
        "car"
      ],
      "subPages": []
    },
    {
      "name": "P2",
      "weight": 56,
      "url": "www.p2.com",
      "stats": {
        "id": 2,
        "lastVisit": null,
        "totalVisit": 0
      },
      "tags": [
        "review",
        "car"
      ],
      "subPages": []
    },
    {
      "name": "P4",
      "weight": 56,
      "url": "www.p4.com",
      "stats": {
        "id": 4,
        "lastVisit": null,
        "totalVisit": 0
      },
      "tags": [
        "toyota",
        "car"
      ],
      "subPages": []
    },
    {
      "name": "P5",
      "weight": 56,
      "url": "www.p5.com",
      "stats": {
        "id": 5,
        "lastVisit": null,
        "totalVisit": 0
      },
      "tags": [
        "honda",
        "car"
      ],
      "subPages": []
    },
    {
      "name": "P1",
      "weight": 56,
      "url": "www.p1.com",
      "stats": {
        "id": 1,
        "lastVisit": null,
        "totalVisit": 0
      },
      "tags": [
        "ford",
        "car",
        "review"
      ],
      "subPages": []
    },
    {
      "name": "P7",
      "weight": 56,
      "url": "www.p7.com",
      "stats": {
        "id": 1,
        "lastVisit": null,
        "totalVisit": 0
      },
      "tags": [
        "ford",
        "car",
        "review"
      ],
      "subPages": []
    }
  ]
}
```

##5.DELETE

delete is to delete a page and all its associated sub-pages from table

###<i>Request</i>

```sh
DELETE /delete
Content-Type: application/json
["P1","P2"]
```

###<i>Response</i>

```sh
{
  "delete_success": true
}
```

##6.CLEAR

clear is to permanently clear the in-memory cache, historic searches from table and lucene indices

###<i>Request</i>

```sh
GET /clear
```

###<i>Response</i>

```sh
{
  "in_memory_cache": true,
  "page_indices": true,
  "lucene_indices": true
}
```
