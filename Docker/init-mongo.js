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
)