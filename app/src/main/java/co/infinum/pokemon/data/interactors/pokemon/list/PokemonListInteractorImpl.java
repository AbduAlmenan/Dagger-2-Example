package co.infinum.pokemon.data.interactors.pokemon.list;

import javax.inject.Inject;

import co.infinum.pokemon.data.models.Pokedex;
import co.infinum.pokemon.data.remote.PokemonService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dino on 21/03/15.
 */
public class PokemonListInteractorImpl implements PokemonListInteractor, Callback<Pokedex> {

    private PokemonListListener pokemonListListener;

    private boolean isCanceled;

    private PokemonService pokemonService;

    @Inject
    public PokemonListInteractorImpl(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @Override
    public void loadPokemonList() {
        reset();
        pokemonService.getPokedex(this);
    }

    @Override
    public void provideListener(PokemonListListener listener) {
        this.pokemonListListener = listener;
    }

    @Override
    public void cancel() {
        isCanceled = true;
    }

    @Override
    public void reset() {
        isCanceled = false;
    }

    @Override
    public void success(Pokedex pokedex, Response response) {
        if (!isCanceled) {
            pokemonListListener.onSuccess(pokedex);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        if (!isCanceled) {
            pokemonListListener.onFailure(error.getMessage());
        }
    }
}