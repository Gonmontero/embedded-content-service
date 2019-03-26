insert into provider (provider_name, api_url)
values('youtube', 'https://www.youtube.com/');

insert into url_schemas
values('youtube', 'https(:|%3A)(\/|%2F)(\/|%2F)www.youtube.com(\/|%2F)([^*$])+');

insert into provider (provider_name, api_url)
values('spotify', 'https://embed.spotify.com/');

insert into url_schemas
values('spotify', 'spotify(:|%3A)([^*$])+');

insert into url_schemas
values('spotify', 'https(:|%3A)(\/|%2F)(\/|%2F)([^*$])+.spotify.com/([^*$])+');
