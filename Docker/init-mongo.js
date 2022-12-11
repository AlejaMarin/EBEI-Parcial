db = db.getSiblingDB('series-dev');
db.createUser(
    {
        user: "usr-series",
        pwd: "pwd-series",
        roles: [
            {
                role: "readWrite",
                db: "series-dev"
            }
        ]
    }
);

db = db.getSiblingDB('catalog-dev');
db.createUser(
    {
        user: "usr-catalog",
        pwd: "pwd-catalog",
        roles: [
            {
                role: "readWrite",
                db: "catalog-dev"
            }
        ]
    }
);