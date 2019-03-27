insert into provider (provider_name, api_url)
values('youtube', 'https://www.youtube.com/');

insert into url_schemas
values('youtube', '(http|https)(:|%3A)(\/|%2F)(\/|%2F)www.youtube.com(\/|%2F)([^*$])+');

insert into url_schemas
values('youtube', '(http|https)(:|%3A)(\/|%2F)(\/|%2F)youtube.com(\/|%2F)([^*$])+');

insert into url_schemas
values('youtube', '(http|https)(:|%3A)(\/|%2F)(\/|%2F)ytube.com(\/|%2F)([^*$])+');

insert into provider (provider_name, api_url)
values('spotify', 'https://embed.spotify.com/');

insert into url_schemas
values('spotify', 'spotify(:|%3A)([^*$])+');

insert into url_schemas
values('spotify', '(http|https)(:|%3A)(\/|%2F)(\/|%2F)([^*$])+.spotify.com/([^*$])+');
